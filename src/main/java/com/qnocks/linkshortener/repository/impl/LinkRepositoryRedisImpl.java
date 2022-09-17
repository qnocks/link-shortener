package com.qnocks.linkshortener.repository.impl;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class LinkRepositoryRedisImpl implements LinkRepository {

    private final ReactiveRedisOperations<String, String> storage;

    @Override
    public Mono<Link> save(Link link) {
        return storage.opsForValue()
                .set(link.getShortUrl(), link.getOriginUrl())
                .map(b -> link);
    }

    @Override
    public Mono<Link> findByOriginUrl(String url) {
        return storage.opsForValue()
                .get(url)
                .map(shortUrl -> Link.builder()
                        .originUrl(url)
                        .shortUrl(shortUrl)
                        .build());
    }
}
