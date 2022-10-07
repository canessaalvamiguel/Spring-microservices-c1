package com.example.productsservice.controller;

import com.example.productsservice.entity.Product;
import com.example.productsservice.service.IProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final IProductService productService;

    private Environment env;

    public ProductController(IProductService productService, Environment env) {
        this.productService = productService;
        this.env = env;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.findAll()
                .stream()
                .map(x -> {
                    x.setPort(Integer.parseInt(env.getProperty("local.server.port")));
                    return x;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long id) throws InterruptedException {
        Product product = productService.findById(id);
        product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        //Thread.sleep(2000L);
        return product;
    }
}
