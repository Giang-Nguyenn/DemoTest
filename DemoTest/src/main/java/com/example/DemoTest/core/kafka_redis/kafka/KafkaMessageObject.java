package com.example.DemoTest.core.kafka_redis.kafka;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@NoArgsConstructor
public class KafkaMessageObject {
    Long id;
    String status;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime createdAt;

    public KafkaMessageObject(String message){
//        1,START,2022-01-15T22:21:04.216
        String[] messageKafkaToArray = message.split(",");
        this.id= Long.parseLong(messageKafkaToArray[0]);
        this.status= messageKafkaToArray[1];
        this.createdAt = LocalDateTime.parse(messageKafkaToArray[2]);
    }

    public String toMessage(){
        return  this.id+","+ this.status+","+this.createdAt;
    }
}
