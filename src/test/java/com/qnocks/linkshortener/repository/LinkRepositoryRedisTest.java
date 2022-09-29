package com.qnocks.linkshortener.repository;

import com.qnocks.linkshortener.AbstractIntegrationTest;
import com.qnocks.linkshortener.TestRedisConfiguration;
import com.qnocks.linkshortener.dto.Link;
import com.qnocks.linkshortener.repository.impl.LinkRepositoryRedisImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestRedisConfiguration.class)
public class LinkRepositoryRedisTest extends AbstractIntegrationTest {

    @Autowired
    private LinkRepositoryRedisImpl underTest;

    @Test
    void shouldSave() {
        val url = "https://www.google.ru/search?q=hello+world";
        val expectedShortUrl = "4rJ5d4";
        val link = Link.builder()
                .originUrl(url)
                .shortUrl(expectedShortUrl)
                .build();

        StepVerifier
                .create(underTest.save(link))
                .expectNext(link)
                .verifyComplete();
    }

    // TODO: figure out why test job completes fine, the specific test class failed
    @Test
    void shouldFindByShortUrl() {
        val url = "https://www.google.ru/search?q=hello+world";
        val shortUrl = "4rJ5d4";

        StepVerifier
                .create(underTest.findByShortUrl(shortUrl))
                .assertNext(l -> assertThat(l.getOriginUrl().equals(url)).isTrue())
                .expectComplete()
                .verify(Duration.ofSeconds(3));
    }
}
