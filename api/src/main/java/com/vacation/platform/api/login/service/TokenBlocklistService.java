package com.vacation.platform.api.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenBlocklistService {

    private final StringRedisTemplate redisTemplate;

    public void addToBlocklist(String token, long expiration){
        redisTemplate.opsForValue().set(token, "block", expiration, TimeUnit.MILLISECONDS);
    }

    public boolean isBlockListed(String token){
        return redisTemplate.hasKey(token);
    }

}
