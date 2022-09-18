package com.qnocks.linkshortener.service.impl;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.repository.LinkRepository;
import com.qnocks.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final Random random;

    @Override
    public Mono<Link> createShortLink(Link link) {
        var shortenUrl = shortenUrl(link.getOriginUrl());
        link.setShortUrl(shortenUrl);
        return linkRepository.save(link);
    }

    @Override
    public Mono<Link> getOriginUrl(String url) {
        return linkRepository.findByShortUrl(url);
    }

    private String shortenUrl(String url) {
        return RandomStringUtils.random(6, true, true) +
                url.charAt(random.nextInt(url.length()));
    }
}

