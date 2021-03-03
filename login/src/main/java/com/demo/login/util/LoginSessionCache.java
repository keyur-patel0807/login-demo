package com.demo.login.util;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.inject.Singleton;

import java.util.concurrent.TimeUnit;

@Singleton
public class LoginSessionCache {

    private final Cache<String, String> localCache;

    public LoginSessionCache(){

        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };
        this.localCache = CacheBuilder.newBuilder().build(loader);
    }

    public Cache<String, String> getLocalCache() {
        return this.localCache;
    }
}
