package com.workoutTracker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(value = "com.workoutTracker.repository")
public class RedisConfig {
}
