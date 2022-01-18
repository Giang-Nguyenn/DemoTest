package com.example.DemoTest.core.kafka_redis.redis;

//public class RedisConfig {
//}


import com.example.DemoTest.core.kafka_redis.kafka.KafkaMessageObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jdk.jfr.Enabled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

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
