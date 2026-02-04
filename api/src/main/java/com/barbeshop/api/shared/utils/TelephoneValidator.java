package com.barbeshop.api.shared.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelephoneValidator implements ConstraintValidator<Telephone, String> {

    @Override
    public void initialize(Telephone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String cleanNumber= value.replaceAll("\\D", "");

        return cleanNumber.matches("^[1-9]{2}9\\d{8}$");
    }
}
