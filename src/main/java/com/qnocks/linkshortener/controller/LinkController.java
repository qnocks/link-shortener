package com.qnocks.linkshortener.controller;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @GetMapping("{url}")
    public Mono<ResponseEntity<Void>> redirect(@PathVariable String url) {
        return linkService.getOriginUrl(url)
                .flatMap(res -> Mono.just(ResponseEntity
                        .status(HttpStatus.FOUND)
                        .location(URI.create(res.getOriginUrl()))
                        .build()));
    }

    @PostMapping("/api/links")
    public Mono<Link> createShortLink(@RequestBody Link link) {
        return linkService.createShortLink(link);
    }
}
