package com.barbeshop.api.controller;

import com.barbeshop.api.dto.product.ProductCreateDTO;
import com.barbeshop.api.dto.product.ProductResponseDTO;
import com.barbeshop.api.dto.product.ProductUpdateDTO;
import com.barbeshop.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProductsByCategoryName(@PathVariable String categoryName,
                                                                                 @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProductsByCategoryName(categoryName, pageable));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProductsByName(@PathVariable String name,
                                                                        @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProductsByName(name, pageable));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO newProductDTO) {
        ProductResponseDTO createdProduct = productService.createProduct(newProductDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.id())
                .toUri();
        return ResponseEntity.created(location).body(createdProduct);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable String id,
                                                            @Valid @RequestBody ProductUpdateDTO updatedProductDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, updatedProductDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}
