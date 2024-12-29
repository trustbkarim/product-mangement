package com.product.services.impl;

import com.product.dao.dto.ProductDto;
import com.product.dao.entities.ProductEntity;
import com.product.dao.repositories.ProductRepository;
import com.product.exceptions.ResourceNotFoundException;
import com.product.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }


    public ProductDto createProduct(ProductDto productDto) {
        ProductEntity product = modelMapper.map(productDto, ProductEntity.class);
        ProductEntity savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    public List<ProductDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return modelMapper.map(product, ProductDto.class);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {

        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        modelMapper.map(productDto, existingProduct);

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
