package com.demo.login.repository;

import com.demo.login.model.Userlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<Userlog, Long> {
}