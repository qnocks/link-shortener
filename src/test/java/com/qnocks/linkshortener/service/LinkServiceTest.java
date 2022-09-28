package com.qnocks.linkshortener.service;

import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.repository.LinkRepository;
import com.qnocks.linkshortener.service.impl.LinkServiceImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {

    @InjectMocks
    private LinkServiceImpl underTest;

    @Mock
    private StringShortener shortener;

    @Mock
    private LinkRepository linkRepository;

    @Test
    void shouldCreateShortLink() {
        val url = "https://www.google.ru/search?q=hello+world";
        val shortUrl = "4rJ5d4";
        val link = Link.builder()
                .originUrl(url)
                .build();

        when(shortener.shorten(url)).thenReturn(shortUrl);
        when(linkRepository.save(link)).thenReturn(Mono.just(link));

        StepVerifier
                .create(underTest.createShortLink(link))
                .consumeNextWith(l -> assertThat(l.getShortUrl().equals(shortUrl)))
                .verifyComplete();
    }

    @Test
    void shouldGetOriginUrl() {
        val url = "https://www.google.ru/search?q=hello+world";
        val link = Link.builder()
                .originUrl(url)
                .build();

        when(linkRepository.findByShortUrl(url)).thenReturn(Mono.just(link));

        StepVerifier
                .create(underTest.getOriginUrl(url))
                .consumeNextWith(l -> assertThat(l.getOriginUrl().equals(url)))
                .verifyComplete();
    }
}
