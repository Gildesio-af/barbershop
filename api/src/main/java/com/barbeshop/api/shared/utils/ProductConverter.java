package com.barbeshop.api.shared.utils;

import com.barbeshop.api.dto.category.CategoryResponseDTO;
import com.barbeshop.api.dto.product.ProductCreateDTO;
import com.barbeshop.api.dto.product.ProductResponseDTO;
import com.barbeshop.api.dto.product.ProductUpdateDTO;
import com.barbeshop.api.model.Category;
import com.barbeshop.api.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public static ProductResponseDTO modelToResponseDTO(Product product) {
        if (product == null) return null;

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .minStock(product.getMinStock())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .isVisible(product.isVisible())
                .category(CategoryResponseDTO
                        .builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build())
                .build();
    }

    public static Product createDTOToModel(ProductCreateDTO newProductDTO, Category category) {
        if (newProductDTO == null) return null;

        return Product.builder()
                .name(newProductDTO.name())
                .quantity(newProductDTO.quantity())
                .minStock(newProductDTO.minStock())
                .price(newProductDTO.price())
                .category(category)
                .imageUrl(newProductDTO.imageUrl())
                .isVisible(newProductDTO.isVisible())
                .build();
    }

    public static void updateModelFromDTO(Product product, ProductUpdateDTO updateDTO) {
        if (product == null || updateDTO == null) return;

        if(updateDTO.name() != null) product.setName(updateDTO.name());
        if(updateDTO.quantity() != null) product.setQuantity(updateDTO.quantity());
        if(updateDTO.minStock() != null) product.setMinStock(updateDTO.minStock());
        if(updateDTO.price() != null) product.setPrice(updateDTO.price());
        if(updateDTO.categoryId() != null && !updateDTO.categoryId().isBlank())
            product.setCategory(Category.builder()
                    .id(product.getCategory().getId())
                    .build());
        if(updateDTO.imageUrl() != null && !updateDTO.imageUrl().isBlank()) product.setImageUrl(updateDTO.imageUrl());
        if(updateDTO.isVisible() != null) product.setVisible(updateDTO.isVisible());
    }
}
