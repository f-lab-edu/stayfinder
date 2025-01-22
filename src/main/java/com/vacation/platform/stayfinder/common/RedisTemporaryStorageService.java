package com.vacation.platform.stayfinder.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisTemporaryStorageService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis 데이터 저장
     * @param key
     * @param value
     * @param timeoutSeconds
     */
    public void saveTemporaryData(String key, Object value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeoutSeconds));
    }


    /**
     * Redis 데이터 가져오기
     * @param key
     * @return
     */
    public Object getTemporaryData(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * Redis 데이터 삭제
     * @param key
     */
    public void deleteTemporaryData(String key) {
        redisTemplate.delete(key);
    }

}
