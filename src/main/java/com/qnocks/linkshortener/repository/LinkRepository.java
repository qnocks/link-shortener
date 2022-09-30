package com.qnocks.linkshortener.repository;

import com.qnocks.linkshortener.dto.Link;
import reactor.core.publisher.Mono;

public interface LinkRepository {

    Mono<Link> save(Link link);
    Mono<Link> findByShortUrl(String url);
    void update(Link link);
}
