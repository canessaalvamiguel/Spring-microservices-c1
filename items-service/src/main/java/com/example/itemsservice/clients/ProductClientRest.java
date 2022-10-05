package com.example.itemsservice.clients;

import com.example.itemsservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "products-service", url = "localhost:8001")
public interface ProductClientRest {

    @GetMapping("/products")
    public List<Product> list();

    @GetMapping("/products/{id}")
    public Product details(@PathVariable("id") Long id);
}
