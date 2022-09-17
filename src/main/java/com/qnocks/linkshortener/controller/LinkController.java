package com.qnocks.linkshortener.controller;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
public class LinkController {

    // TODO: change controller's handlers to reactive ones

    private final LinkService linkService;

    @GetMapping("{url}")
    public ResponseEntity<Void> redirect(@PathVariable String url) {
        var link = linkService.getOriginUrl(url);
        AtomicReference<String> lol = new AtomicReference<>("");
        link.doOnSuccess(l -> lol.set(l.getOriginUrl()));
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(lol.get()))
                .build();
    }

    @PostMapping
    public Mono<Link> createShortLink(@RequestBody Link link) {
        return linkService.createShortLink(link);
    }
}
