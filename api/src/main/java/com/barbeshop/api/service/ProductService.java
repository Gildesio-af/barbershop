package com.barbeshop.api.service;

import com.barbeshop.api.dto.product.ProductCreateDTO;
import com.barbeshop.api.dto.product.ProductResponseDTO;
import com.barbeshop.api.dto.product.ProductUpdateDTO;
import com.barbeshop.api.model.Category;
import com.barbeshop.api.model.Product;
import com.barbeshop.api.repository.CategoryRepository;
import com.barbeshop.api.repository.ProductRepository;
import com.barbeshop.api.shared.exception.EntityNotFoundException;
import com.barbeshop.api.shared.utils.ProductConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO getProductById(String id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));

        return ProductConverter.modelToResponseDTO(product);
    }

    public Page<ProductResponseDTO> getAllProductsByCategoryName(String categoryName, Pageable pageable) {
        var products = productRepository.findAllByCategory_NameContainingIgnoreCase(categoryName, pageable);

        return products.map(ProductConverter::modelToResponseDTO);
    }

    public Page<ProductResponseDTO> getAllProductsByName(String name, Pageable pageable) {
        var products = productRepository.findAllByNameContainingIgnoreCase(name, pageable);

        return products.map(ProductConverter::modelToResponseDTO);
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO newProductDTO) {
        Category categoryEntity = categoryRepository.findByNameIgnoreCase(newProductDTO.categoryName())
                .orElseGet(() -> categoryRepository.save(new Category(null, newProductDTO.categoryName())));

        Product productModel = ProductConverter.createDTOToModel(newProductDTO, categoryEntity);

        return ProductConverter.modelToResponseDTO(productRepository.save(productModel));
    }

    @Transactional
    public ProductResponseDTO updateProduct(String id, ProductUpdateDTO updatedProductDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));

        if(updatedProductDTO.categoryId() != null && !categoryRepository.existsById(updatedProductDTO.categoryId()))
            throw new EntityNotFoundException("Category", updatedProductDTO.categoryId());

        ProductConverter.updateModelFromDTO(product, updatedProductDTO);

        return ProductConverter.modelToResponseDTO(productRepository.save(product));
    }

    @Transactional
    public void deleteProductById(String id) {
        if (!productRepository.existsById(id))
            throw new EntityNotFoundException("Product", id);
        productRepository.deleteById(id);
    }
}
