package com.barbeshop.api.repository;

import com.barbeshop.api.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Page<Category> findAllByName(String name, Pageable pageable);
}
