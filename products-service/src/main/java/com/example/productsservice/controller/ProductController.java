package com.example.productsservice.controller;

import com.example.commonsserver.models.Product;
import com.example.productsservice.service.IProductService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
        if(id.equals(10L))
            throw new IllegalStateException("Product not found");
        if(id.equals(7L))
            TimeUnit.SECONDS.sleep(7L);
        return product;
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product edit(@RequestBody Product product, @PathVariable Long id){
        Product productDb = productService.findById(id);
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());

        return productService.save(productDb);
    }

    @DeleteMapping("/products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        productService.deleteById(id);
    }
}
