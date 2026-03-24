package com.barbeshop.api.repository;

import com.barbeshop.api.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAllByCategory_NameContainingIgnoreCase(String categoryName, Pageable pageable);

    @EntityGraph(attributePaths = {"category"})
    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Query("UPDATE Product p SET p.isActive = false WHERE p.id = :id AND p.isActive = true")
    int softDeleteById(String id);

    @Query("SELECT p FROM Product p WHERE p.id IN :ids")
    List<Product> findAllByIdInList(@Param("ids") List<String> ids);
}
