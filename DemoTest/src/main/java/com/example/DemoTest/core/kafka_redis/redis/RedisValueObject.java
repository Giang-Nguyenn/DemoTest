package com.example.DemoTest.core.kafka_redis.redis;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class RedisValueObject {
    Long id;
    String status;
    LocalDateTime lastEventTime;
    Long point;
    Long pointInDay;

    public RedisValueObject(String message){
        //        1,START,2022-01-15T22:21:04.216,point,pointInDay
        String[] messageRedisToArray = message.split(",");
        this.id= Long.parseLong(messageRedisToArray[0]);
        this.status= messageRedisToArray[1];
        this.lastEventTime = LocalDateTime.parse(messageRedisToArray[2]);
        this.point=Long.parseLong(messageRedisToArray[3]);
        this.pointInDay=Long.parseLong(messageRedisToArray[4]);
    }

    public String toValue(){
        return this.id+","+this.status+","+this.lastEventTime+","+this.point+","+this.pointInDay;
    }

}
