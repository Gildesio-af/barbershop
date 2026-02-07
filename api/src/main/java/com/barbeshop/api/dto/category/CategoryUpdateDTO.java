package com.barbeshop.api.dto.category;

import org.hibernate.validator.constraints.Length;

public record CategoryUpdateDTO(
        @Length(min = 3, max = 50, message = "Category name must be between 3 and 50 characters")
        String name) {
}
