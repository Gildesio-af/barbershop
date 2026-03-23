package com.barbeshop.api.shared.exception;

import org.springframework.http.HttpStatus;

public class CustomerHasPendingAppointmentException extends ApiException{
    public CustomerHasPendingAppointmentException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
