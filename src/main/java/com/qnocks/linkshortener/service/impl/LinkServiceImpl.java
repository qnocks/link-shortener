package com.qnocks.linkshortener.service.impl;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.repository.LinkRepository;
import com.qnocks.linkshortener.service.LinkService;
import com.qnocks.linkshortener.service.StringShortener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final StringShortener shortener;

    @Override
    public Mono<Link> createShortLink(Link link) {
        link.setShortUrl(shortenUrl(link.getOriginUrl()));
        return linkRepository.save(link);
    }

    @Override
    public Mono<Link> getOriginUrl(String url) {
        return linkRepository.findByShortUrl(url);
    }

    private String shortenUrl(String url) {
        return shortener.shorten(url);
    }
}

