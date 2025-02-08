package com.vacation.platform.stayfinder.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final StringRedisTemplate stringRedisTemplate;

    public void saveToken(String email, String token, long seconds, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(email, token, seconds, timeUnit);
    }

    public boolean checkToken(String email, String refreshToken) {
        return Objects.requireNonNull(stringRedisTemplate.opsForValue().get(email)).equals(refreshToken);
    }

    public void deleteToken(String email) {
        stringRedisTemplate.delete(email);
    }

}