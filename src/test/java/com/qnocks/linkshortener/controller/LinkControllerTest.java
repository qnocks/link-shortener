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
    void shouldCreateShortLink() {
        val url = "https://www.google.ru/search?q=hello+world";
        val expectedShortUrl = "647sda";
        val link = Link.builder()
                .originUrl(url)
                .shortUrl(expectedShortUrl)
                .build();

        when(linkService.createShortLink(link)).thenReturn(Mono.just(link));

        webClient
                .post().uri("/api/links")
                .bodyValue(link)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("originUrl").isEqualTo(url)
                .jsonPath("shortUrl").isEqualTo(expectedShortUrl)
                .jsonPath("redirectCount").isEqualTo(0);
    }

    @Test
    void shouldGetLinkInfo() {
        val url = "https://www.google.ru/search?q=hello+world";
        val shortUrl = "647sda";
        val link = Link.builder()
                .shortUrl(shortUrl)
                .originUrl(url)
                .redirectCount(3)
                .build();

        when(linkService.getOriginUrl(shortUrl)).thenReturn(Mono.just(link));

        webClient
                .get().uri("/api/links/{url}", shortUrl)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("originUrl").isEqualTo(url)
                .jsonPath("shortUrl").isEqualTo(shortUrl)
                .jsonPath("redirectCount").isEqualTo(link.getRedirectCount());
    }
}

