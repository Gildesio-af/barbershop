package com.barbeshop.api.dto.setting;

import com.barbeshop.api.shared.utils.validation.Telephone;
import org.hibernate.validator.constraints.Length;

public record StoreSettingUpdateDTO(
        @Length(min = 3, max = 100, message = "Store name must be between 3 and 100 characters")
        String name,
        @Length(max = 150, message = "Address must be at most 150 characters")
        String address,
        @Telephone(message = "Invalid phone number format")
        String phone,
        String instagram,
        Boolean productsEnabled
) {
}
