package com.qnocks.linkshortener.exception.handler;

import com.qnocks.linkshortener.exception.custom.LinkException;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(LinkException.class)
    public ResponseEntity<ErrorResponse> handleTransactionException(LinkException e) {
        return buildResponse(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status) {
        val response = ErrorResponse.builder()
                .message(message)
                .status(status.value())
                .error(status)
                .timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
