package com.qnocks.linkshortener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class RedisConfigurationTest extends AbstractIntegrationTest {

    @Value("${spring.redis.port}")
    private int port;
    private final RedisServer redisServer;

    public RedisConfigurationTest() {
        redisServer = new RedisServer(6666);
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
