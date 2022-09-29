package com.example.productsservice.service;

import com.example.productsservice.entity.Product;
import com.example.productsservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        Boolean exists = productRepository.existsById(id);
        if(!exists)
            new RuntimeException("Product with id "+id+" doesn't exists");

        return productRepository.findById(id).get();
    }
}
