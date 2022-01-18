package com.example.DemoTest.core.kafka_redis.kafka;

import com.example.DemoTest.core.kafka_redis.redis.RedisValueObject;
import com.example.DemoTest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyTopicConsumer {
    @Autowired
    UserService userService;
    @Autowired
    private RedisTemplate redisTemplateWithJsonSerializer;
    private final List<String> messages = new ArrayList<>();


    @KafkaListener(topics = "test", groupId = "kafka-sandbox")
    public void listen(String message) {

        System.out.println("Messege :" +message);
        synchronized (messages) {
//            1,START,2022-01-15T22:21:04.216
//            String[] messageKafkaToArray = message.split(",");
            KafkaMessageObject kafkaMessageObject=new KafkaMessageObject(message);
            String key = kafkaMessageObject.getId()+"_"+kafkaMessageObject.getCreatedAt().toString().substring(0,10);
//            String value = (String) redisTemplateWithJsonSerializer.opsForValue().get(key);
//            redisTemplateWithJsonSerializer.opsForValue().set(key,kafkaMessageObject);
//            KafkaMessageObject object= (KafkaMessageObject) redisTemplateWithJsonSerializer.opsForValue().get(key);
//            System.out.println(object.getStatus());
//            System.out.println(redisTemplateWithJsonSerializer.opsForValue().get(key));
//            if(value != null){
//                // 1,Status,lastTime,point,sumpoint
//                RedisValueObject redisValueObject=new RedisValueObject(value);
//                Long pointInDay=redisValueObject.getPointInDay();
//                if( pointInDay< 2400){
//                    long second = ChronoUnit.SECONDS.between(redisValueObject.getLastEventTime(),
//                                                            kafkaMessageObject.getCreatedAt());
//                    Long newPoint=redisValueObject.getPoint()+second/3;
//                    if(newPoint >20) {
//                        if((pointInDay+newPoint)>2400){
//                            // cộng vào db 2400-pointInday,
//                            userService.updatePoint(kafkaMessageObject.getId(),2400-pointInDay);
//                            // lưu point trên redis là 2400
//                            RedisValueObject newRedisvalue= new RedisValueObject(kafkaMessageObject.getId(),
//                                                                            kafkaMessageObject.getStatus(),
//                                                                            kafkaMessageObject.getCreatedAt(),
//                                                                                                    0L,
//                                                                                            2400L
//                                                                                                             );
//                            redisTemplateWithJsonSerializer.opsForValue().set(key,newRedisvalue.toValue());
//                        }
//                        else {
//                            //công vào db newpoint
//                            userService.updatePoint(kafkaMessageObject.getId(),newPoint);
//                            // lưu vào redis pointInDay mới và cập nhật lastDate
//                            RedisValueObject newRedisvalue= new RedisValueObject(kafkaMessageObject.getId(),
//                                                                                kafkaMessageObject.getStatus(),
//                                                                                kafkaMessageObject.getCreatedAt(),
//                                                                                                        0L,
//                                                                                    pointInDay+newPoint
//                            );
//                            redisTemplateWithJsonSerializer.opsForValue().set(key,newRedisvalue.toValue());
//                        }
//
//                    }
//                }
//            }
//            else {
//                // 1,Status,lastTime,point,sumpoint
//                String newValue = kafkaMessageObject.getId()+","+ kafkaMessageObject.getStatus()+","+kafkaMessageObject.getCreatedAt()+ ",0"+",0";
//                redisTemplateWithJsonSerializer.opsForValue().set(key,newValue);
//            }
//            redisTemplate.opsForValue().set("keyt","valuet");
//            messages.add(message);
        }
    }

    public List<String> getMessages() {
        return messages;
    }

}