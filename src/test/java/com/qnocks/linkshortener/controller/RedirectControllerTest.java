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

@WebFluxTest(RedirectController.class)
public class RedirectControllerTest extends AbstractIntegrationTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private LinkServiceImpl linkService;

    @Test
    void shouldRedirect() {
        val url = "https://www3.zoechip.com";
        val shortUrl = "87jbF6";
        val link = Link.builder()
                .originUrl(url)
                .shortUrl(shortUrl)
                .redirectCount(3)
                .build();

        when(linkService.processRedirect(shortUrl)).thenReturn(Mono.just(link));

        webClient
                .get().uri("/{url}", shortUrl)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().location(url)
                .expectBody().isEmpty();
    }
}
