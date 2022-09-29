package com.qnocks.linkshortener.controller;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping
    public Mono<Link> createShortLink(@RequestBody Link link) {
        return linkService.createShortLink(link);
    }

    @GetMapping("{url}")
    public Mono<Link> getLinkInfo(@PathVariable String url) {
        return linkService.getOriginUrl(url);
    }
}
