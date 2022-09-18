package com.qnocks.linkshortener.repository.impl;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LinkRepositoryRedisImpl implements LinkRepository {

    private final ReactiveRedisOperations<String, String> storage;

    @Override
    public Mono<Link> save(Link link) {
        log.info("Saving: " + link);
        return storage.opsForValue()
                .set(link.getShortUrl(), link.getOriginUrl())
                .map(b -> link);
    }

    @Override
    public Mono<Link> findByShortUrl(String shortUrl) {
        return storage.opsForValue()
                .get(shortUrl)
                .map(url -> Link.builder()
                        .originUrl(url)
                        .shortUrl(shortUrl)
                        .build());
    }
}
