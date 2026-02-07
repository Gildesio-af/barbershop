package com.barbeshop.api.service;

import com.barbeshop.api.dto.category.CategoryResponseDTO;
import com.barbeshop.api.dto.category.CategoryUpdateDTO;
import com.barbeshop.api.model.Category;
import com.barbeshop.api.repository.CategoryRepository;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO getCategoryById(String id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));

        return new CategoryResponseDTO(category.getId(), category.getName());
    }

    public Page<CategoryResponseDTO> getAllCategoriesByName(String name, Pageable pageable) {
        var categories = categoryRepository.findAllByName(name, pageable);

        return categories.map(category ->
                new CategoryResponseDTO(category.getId(), category.getName()));
    }

    @Transactional
    public CategoryResponseDTO updateCategory(String id, CategoryUpdateDTO updateDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));

        Optional<Category> existingCategory = categoryRepository.findByNameIgnoreCase(updateDTO.name());
        if (existingCategory.isPresent() && !existingCategory.get().getId().equals(id))
            throw new IllegalArgumentException("Category name already exists: " + updateDTO.name());

        category.setName(updateDTO.name());
        categoryRepository.save(category);

        return new CategoryResponseDTO(category.getId(), category.getName());
    }
}
