package com.example.itemsservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.example.commonsserver.models.Product;
import java.util.List;

@FeignClient(name = "products-service")
public interface ProductClientRest {

    @GetMapping("/products")
    public List<Product> list();

    @GetMapping("/products/{id}")
    public Product details(@PathVariable("id") Long id);

    @PostMapping("/products")
    public Product create(@RequestBody Product product);

    @PutMapping("/products/{id}")
    public Product update(@RequestBody Product product, @PathVariable("id") Long id);

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable("id") Long id);
}
