package com.vacation.platform.stayfinder.login.service;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenRedisService {

    private final StringRedisTemplate stringRedisTemplate;

    public void saveToken(String email, String token, long seconds, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(email, token, seconds, timeUnit);
    }

    public Optional<String> getToken(String token) {
        return Stream.of(token)
                .map(stringRedisTemplate.opsForValue()::get)
                .filter(Objects::nonNull)
                .findFirst()
                .or(() -> {throw new StayFinderException(
                        ErrorType.TOKEN_IS_NOT_VALID,
                        Map.of("token", token),
                        log::error);
                });
    }

    public void deleteToken(String data) {
        stringRedisTemplate.delete(data);
    }

}