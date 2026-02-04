package com.barbeshop.api.shared.exception;

import org.springframework.http.HttpStatus;

public class InvalidAuthenticationException extends ApiException {
    public InvalidAuthenticationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
