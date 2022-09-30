package com.qnocks.linkshortener.controller;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/links")
@RequiredArgsConstructor
@Tag(name = "Link Controller", description = "API to manage links")
public class LinkController {

    private final LinkService linkService;

    @Operation(summary = "Create short link", description = "Generate short url for the given one")
    @PostMapping
    public Mono<Link> createShortLink(@Valid @RequestBody Link link) {
        return linkService.createShortLink(link);
    }

    @Operation(summary = "Get Link info", description = "Show short url and redirect counts for the url")
    @GetMapping("{url}")
    public Mono<Link> getLinkInfo(@PathVariable String url) {
        return linkService.getOriginUrl(url);
    }
}
