package com.barbeshop.api.repository;

import com.barbeshop.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAllByCategory_NameContainingIgnoreCase(String categoryName, Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
