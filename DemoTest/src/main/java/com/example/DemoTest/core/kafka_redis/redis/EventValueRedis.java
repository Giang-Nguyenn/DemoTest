package com.example.DemoTest.core.kafka_redis.redis;

import com.example.DemoTest.core.penum.StatusEvent;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventValueRedis {
    Long id;
    StatusEvent status;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime lastEventTime;

    Integer point;
    Integer pointInDay;

//    public RedisValueObject(String message){
//        //        1,START,2022-01-15T22:21:04.216,point,pointInDay
//        String[] messageRedisToArray = message.split(",");
//        this.id= Long.parseLong(messageRedisToArray[0]);
//        this.status= messageRedisToArray[1];
//        this.lastEventTime = LocalDateTime.parse(messageRedisToArray[2]);
//        this.point=Long.parseLong(messageRedisToArray[3]);
//        this.pointInDay=Long.parseLong(messageRedisToArray[4]);
//    }
//
//    public String toValue(){
//        return this.id+","+this.status+","+this.lastEventTime+","+this.point+","+this.pointInDay;
//    }

}
