package com.demo.login.controller;

import com.demo.login.exception.DataNotFoundException;
import com.demo.login.model.Userlog;
import com.demo.login.repository.UserLogRepository;
import com.demo.login.util.LoginSessionCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    LoginSessionCache loginSessionCache = new LoginSessionCache();

    @Autowired
    UserLogRepository repository;

    @PostMapping("/")
    public ResponseEntity<String> getEmployeesPost(
            @RequestHeader(name = "username") String username,
            @RequestHeader(name = "password") String password) {
        String passwordStr = new String(Base64.getDecoder().decode(password));

        Userlog userlog = Userlog.builder().message("Entry happened for username :: " + username + " , password:: " + password).build();
        repository.save(userlog);

        if ("".equalsIgnoreCase(username) && "".equalsIgnoreCase(passwordStr)) {
            throw new DataNotFoundException("/login","username", "password");
        } else if ("admin".equalsIgnoreCase(username) && "admin123!".equalsIgnoreCase(passwordStr)) {
            loginSessionCache.getLocalCache().invalidateAll();
            return new ResponseEntity<>("Login Successfully Admin!!", HttpStatus.OK);
        } else {
            String sessionId = loginSessionCache.getLocalCache().getIfPresent("sessionID");

            if(sessionId == null) {
                loginSessionCache.getLocalCache().put("sessionID", String.valueOf(1));
                return new ResponseEntity<>("Please type correct username and password" + 1, HttpStatus.OK);
            } else {
                Integer count = Integer.valueOf(loginSessionCache.getLocalCache().getIfPresent("sessionID")) + 1;
                loginSessionCache.getLocalCache().put("sessionID", String.valueOf(count));
                if (count > 5) {
                    return new ResponseEntity<>("You are reached maximum login attempts - " + count, HttpStatus.OK);

                }else {
                    return new ResponseEntity<>("Please type correct username and password" + count, HttpStatus.OK);
                }
            }
        }
    }

    @GetMapping("/userlog")
    public List<Userlog> getEmployeesPost(){
        return repository.findAll();
    }
}
