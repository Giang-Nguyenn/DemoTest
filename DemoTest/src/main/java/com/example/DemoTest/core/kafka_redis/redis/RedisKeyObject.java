package com.example.DemoTest.core.kafka_redis.redis;

import java.time.LocalDateTime;

public class RedisKeyObject {
    Long id;
    LocalDateTime createdAt;

    public String toKey(){
        return id+"_"+createdAt.toString().substring(0,10);
    }
}
