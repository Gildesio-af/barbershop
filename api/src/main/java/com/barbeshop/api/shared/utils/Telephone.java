package com.barbeshop.api.shared.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TelephoneValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Telephone {

    String message() default "Invalid telephone number format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
