package com.qnocks.linkshortener.service.impl;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.repository.LinkRepository;
import com.qnocks.linkshortener.service.LinkService;
import com.qnocks.linkshortener.service.StringShortener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final StringShortener shortener;

    @Override
    public Mono<Link> createShortLink(Link link) {
        link.setShortUrl(shortenUrl(link.getOriginUrl()));
        log.info("Shortened url from [{} to {}]", link.getOriginUrl(), link.getShortUrl());
        return linkRepository.save(link);
    }

    @Override
    public Mono<Link> processRedirect(String originUrl) {
        return linkRepository.findByShortUrl(originUrl).doOnSuccess(link -> {
            link.setRedirectCount(link.getRedirectCount() + 1);
            linkRepository.update(link);
            log.info("Updated redirectCount on [shortUrl={}, originalUrl={}]", originUrl, link.getOriginUrl());
        });
    }

    @Override
    public Mono<Link> getOriginUrl(String originUrl) {
        return linkRepository.findByShortUrl(originUrl).doOnSuccess(link ->
                log.info("Got origin originUrl by [shortUrl={}, originalUrl={}]", originUrl, link.getOriginUrl()));
    }

    private String shortenUrl(String url) {
        return shortener.shorten(url);
    }
}

