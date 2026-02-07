package com.barbeshop.api.controller;

import com.barbeshop.api.dto.category.CategoryResponseDTO;
import com.barbeshop.api.dto.category.CategoryUpdateDTO;
import com.barbeshop.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable String id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategoriesByName(@PathVariable String name, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByName(name, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable String id, @RequestBody CategoryUpdateDTO updateDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, updateDTO));
    }
}
