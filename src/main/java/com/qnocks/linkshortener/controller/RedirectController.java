package com.qnocks.linkshortener.controller;

import com.qnocks.linkshortener.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Redirect Controller", description = "API to redirect to urls")
public class RedirectController {

    private final LinkService linkService;

    @Operation(summary = "Redirect", description = "Redirect to original url by the short one")
    @GetMapping("{url}")
    public Mono<ResponseEntity<Void>> redirect(@PathVariable String url) {
        return linkService.processRedirect(url)
                .flatMap(res -> Mono.just(ResponseEntity
                        .status(HttpStatus.FOUND)
                        .location(URI.create(res.getOriginUrl()))
                        .build()));
    }
}
