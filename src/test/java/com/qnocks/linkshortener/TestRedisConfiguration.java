package com.qnocks.linkshortener;

import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class TestRedisConfiguration extends AbstractIntegrationTest {

    private final RedisServer redisServer;

    public TestRedisConfiguration() {
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
