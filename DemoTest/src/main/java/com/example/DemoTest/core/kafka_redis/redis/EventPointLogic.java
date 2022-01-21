package com.example.DemoTest.core.kafka_redis.redis;//            redisTemplate.opsForValue().set("keyt","valuet");
//            messages.add(message);

import com.example.DemoTest.core.kafka_redis.kafka.EventMessageKafka;
import com.example.DemoTest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class EventPointLogic {
//    @Autowired
    private final RedisTemplate redisTemplateWithJsonSerializer;
//    @Autowired
    private final UserService userService;

    Integer maxPointInDay=2400;
    Integer pointSaveDb=20;
    Integer maxSecond=60;
    Integer secondDivToPoint=3;

    public void updateValueRedisAndDB(EventMessageKafka eventMessageKafka){
        String key = eventMessageKafka.getId()+"_"+eventMessageKafka.getCreatedAt().toString().substring(0,10);
        EventValueRedis eventValueRedis = (EventValueRedis) redisTemplateWithJsonSerializer.opsForValue().get(key);
        if(eventValueRedis != null){
            Integer pointInDay=eventValueRedis.getPointInDay();
            if( pointInDay< maxPointInDay){
                long second = ChronoUnit.SECONDS.between(eventValueRedis.getLastEventTime(),
                        eventMessageKafka.getCreatedAt());
                if(second <= maxSecond){
                    Integer pointPer60second= Math.toIntExact(second / secondDivToPoint); //(20 point for 60s)
                    Integer newPoint=eventValueRedis.getPoint()+pointPer60second;
                    if(newPoint > pointSaveDb) {
                        if((pointInDay+newPoint) > maxPointInDay){
                            userService.updatePoint(eventMessageKafka.getId(),maxPointInDay-pointInDay);
                            updateValueRedis(eventMessageKafka,key,0,maxPointInDay);
                        }
                        else {
                            userService.updatePoint(eventMessageKafka.getId(),newPoint);
                            updateValueRedis(eventMessageKafka,key,0,pointInDay+newPoint);
                        }
                    }
                    else {
                        updateValueRedis(eventMessageKafka,key,newPoint,eventValueRedis.getPointInDay());
                    }
                }
                else {
                    updateValueRedis(eventMessageKafka,key,eventValueRedis.getPoint(),eventValueRedis.getPointInDay());
                }
            }
        }
        else {
            updateValueRedis(eventMessageKafka,key,0,0);
        }
    }

    public void updateValueRedis(EventMessageKafka eventMessageKafka,String key,Integer point,Integer maxPointInDay){
        EventValueRedis newValue = new EventValueRedis( eventMessageKafka.getId(),
                                                        eventMessageKafka.getStatus(),
                                                        eventMessageKafka.getCreatedAt(),
                                                        point,
                                                        maxPointInDay);
        redisTemplateWithJsonSerializer.opsForValue().set(key,newValue);
    }
}
