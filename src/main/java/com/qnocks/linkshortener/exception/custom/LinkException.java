package com.qnocks.linkshortener.exception.custom;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class LinkException extends RuntimeException {

    @Builder.Default
    private String message = "Error occurred while link processing";

    @Builder.Default
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
}
