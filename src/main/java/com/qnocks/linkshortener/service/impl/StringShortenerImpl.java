package com.qnocks.linkshortener.service.impl;

import com.qnocks.linkshortener.service.StringShortener;
import org.springframework.stereotype.Service;

@Service
public class StringShortenerImpl implements StringShortener {

    public static final String ALPHABET = "23456789bcdfghjkmnpqrstvwxyzBCDFGHJKLMNPQRSTVWXYZ-_";
    public static final int BASE = ALPHABET.length();

    @Override
    public String shorten(String s) {
        return encode(Math.abs(decode(s)));
    }

    private static String encode(int num) {
        var sb = new StringBuilder();
        while (num > 0) {
            sb.insert(0, ALPHABET.charAt(num % BASE));
            num = num / BASE;
        }
        return sb.toString();
    }

    private static int decode(String s) {
        var hash = 0;
        for (int i = 0; i < s.length(); i++) {
            hash = hash * BASE + ALPHABET.indexOf(s.charAt(i));
        }
        return hash;
    }
}
