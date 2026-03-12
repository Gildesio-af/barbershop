package com.barbeshop.api.shared.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StatusAppointmentValidator implements ConstraintValidator<StatusAppointment, String> {

    private static final String[] VALID_STATUSES = {"PENDING", "CONFIRMED", "CANCELED"};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (String status : VALID_STATUSES) {
            if (status.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
