package com.qnocks.linkshortener.config;

import com.qnocks.linkshortener.dto.Link;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class LinkShortenerConfiguration {

    @Bean
    public ReactiveRedisTemplate<String, Link> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        val serializer = new Jackson2JsonRedisSerializer<>(Link.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Link> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        val context = builder.value(serializer)
                .hashKey(serializer)
                .hashValue(serializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}
