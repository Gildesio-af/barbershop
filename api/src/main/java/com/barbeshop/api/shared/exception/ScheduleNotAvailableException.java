package com.barbeshop.api.shared.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotAvailableException extends ApiException{
    public  ScheduleNotAvailableException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
