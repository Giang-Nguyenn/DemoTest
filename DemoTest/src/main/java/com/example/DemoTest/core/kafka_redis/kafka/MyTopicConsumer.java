package com.example.DemoTest.core.kafka_redis.kafka;

import com.example.DemoTest.core.kafka_redis.redis.EventPointLogic;
import com.example.DemoTest.core.kafka_redis.redis.EventValueRedis;
import com.example.DemoTest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyTopicConsumer {
    @Autowired
    EventPointLogic eventPointLogic;
    private final List<String> messages = new ArrayList<>();


    @KafkaListener(topics = "test", groupId = "kafka-sandbox")
    public void listen(String message) throws JsonProcessingException {

        System.out.println("Messege :" +message);
        synchronized (messages) {
//            1,START,2022-01-15T22:21:04.216
            ObjectMapper objectMapper=new ObjectMapper();
            EventMessageKafka eventMessageKafka=objectMapper.readValue(message, EventMessageKafka.class);
            eventPointLogic.updateValueRedisAndDB(eventMessageKafka);
        }
    }

    public List<String> getMessages() {
        return messages;
    }

}