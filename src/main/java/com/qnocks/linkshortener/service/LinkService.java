package com.qnocks.linkshortener.service;

import com.qnocks.linkshortener.dto.Link;
import reactor.core.publisher.Mono;

public interface LinkService {

    Mono<Link> createShortLink(Link link);
    Mono<Link> processRedirect(String originUrl);
    Mono<Link> getOriginUrl(String originUrl);
}
