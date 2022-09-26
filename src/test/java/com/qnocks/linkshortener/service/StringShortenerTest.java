package com.qnocks.linkshortener.service;

import com.qnocks.linkshortener.service.impl.StringShortenerImpl;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StringShortenerTest {

    private StringShortenerImpl underTest;

    @BeforeAll
    void setup() {
        underTest = new StringShortenerImpl();
    }

    @Test
    void shouldShortenString() {
        val given = List.of("test", "test-test-test", "https://www.google.ru/search?q=hello+world");
        val actual = new ArrayList<String>();

        given.forEach(s -> {
            actual.add(underTest.shorten(s));
        });

        assertThat(actual).allMatch(s -> s.length() <= 6);
    }
}
