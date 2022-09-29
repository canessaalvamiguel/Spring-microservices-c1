package com.example.productsservice.service;

import com.example.productsservice.entity.Product;

import java.util.List;

public interface IProductService {
    public List<Product> findAll();
    public Product findById(Long id);
}
