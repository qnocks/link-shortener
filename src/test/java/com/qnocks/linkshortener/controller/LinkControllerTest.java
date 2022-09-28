package com.qnocks.linkshortener.controller;

import com.qnocks.linkshortener.AbstractIntegrationTest;
import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.service.impl.LinkServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(LinkController.class)
public class LinkControllerTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private LinkServiceImpl linkService;

    @Test
    void shouldRedirect() {
        val url = "https://www.google.ru/search?q=hello+world";
        val shortUrl = "647sda";
        val link = Link.builder()
                .originUrl(url)
                .shortUrl(shortUrl)
                .build();

        when(linkService.getOriginUrl(shortUrl)).thenReturn(Mono.just(link));

        webClient
                .get().uri("/{url}", shortUrl)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location(url)
                .expectBody().isEmpty();
    }

    @Test
    void shouldCreateShortLink() {
        val url = "https://www.google.ru/search?q=hello+world";
        val expectedShortUrl = "647sda";
        val link = Link.builder()
                .originUrl(url)
                .build();

        when(linkService.createShortLink(link)).thenReturn(Mono.just(Link.builder()
                .shortUrl(expectedShortUrl)
                .originUrl(url)
                .build()));

        webClient
                .post().uri("/api/links")
                .bodyValue(link)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("originUrl").isEqualTo(url)
                .jsonPath("shortUrl").isEqualTo(expectedShortUrl);
    }
}

