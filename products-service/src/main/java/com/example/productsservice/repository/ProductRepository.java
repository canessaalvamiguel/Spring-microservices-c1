package com.example.productsservice.repository;

import com.example.commonsserver.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
