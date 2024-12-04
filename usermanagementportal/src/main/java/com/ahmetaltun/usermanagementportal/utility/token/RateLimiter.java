package com.ahmetaltun.usermanagementportal.utility.token;

import com.ahmetaltun.usermanagementportal.exception.RateLimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ahmet Altun
 * @version 1.0
 * @email ahmet.altun60@gmail.com
 * @since 04/12/2024
 */


@Slf4j
@Component
public class RateLimiter {
    private final Map<String, Integer> tokenCount = new HashMap<>();
    private final Map<String, Long> lastResetTime = new HashMap<>();

    @Value("${rate.limit.max-tokens}")
    private int maxTokensPerHour;

    public void checkRateLimit(String userId) {
        long currentTime = System.currentTimeMillis();

        if (shouldResetCount(userId, currentTime)) {
            resetCount(userId, currentTime);
        }

        int currentCount = tokenCount.getOrDefault(userId, 0) + 1;

        if (currentCount > maxTokensPerHour) {
            log.warn("Rate limit exceeded for user: {}", userId);
            throw new RateLimitExceededException("Token generation rate limit exceeded");
        }

        tokenCount.put(userId, currentCount);
    }

    private boolean shouldResetCount(String userId, long currentTime) {
        Long lastReset = lastResetTime.get(userId);
        return lastReset == null || currentTime - lastReset >= TimeUnit.HOURS.toMillis(1);
    }

    private void resetCount(String userId, long currentTime) {
        tokenCount.put(userId, 0);
        lastResetTime.put(userId, currentTime);
    }
}