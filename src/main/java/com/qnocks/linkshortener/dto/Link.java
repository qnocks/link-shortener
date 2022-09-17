package com.qnocks.linkshortener.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Link {

    private String shortUrl;
    private String originUrl;
}
