package com.example.DemoTest.core.kafka_redis.redis;

//public class RedisConfig {
//}


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {
    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;


    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new JedisConnectionFactory();
    }

//    @Bean
//    @Primary
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
////        template.setHashKeySerializer(new StringRedisSerializer());
////        template.setHashKeySerializer(new StringRedisSerializer());
////        template.setValueSerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jsonRedisSerializer);
//        template.setValueSerializer(jsonRedisSerializer);
//        template.setEnableTransactionSupport(true);
////        template.setKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }

//    @Bean
//    RedisTemplate<String, String> redisTemplate() {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }

    @Bean
    public GenericJackson2JsonRedisSerializer getJsonSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(new ObjectMapper().enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY));
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplateWithJsonSerializer(RedisConnectionFactory redisConnectionFactory,
                                                                         GenericJackson2JsonRedisSerializer jsonRedisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

//        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder().failOnEmptyBeans(false)
//                .failOnUnknownProperties(false)
//                .indentOutput(false)
//                .serializationInclusion(JsonInclude.Include.NON_NULL)
//                .modules(
//                        // Optional
//                        new Jdk8Module(),
//                        // Dates/Times
//                        new JavaTimeModule()
//                )
//                .featuresToDisable(
//                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
//                        DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS,
//                        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS
//                ).build();
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jsonRedisSerializer);
        template.setValueSerializer(jsonRedisSerializer);
        return template;
    }
}
