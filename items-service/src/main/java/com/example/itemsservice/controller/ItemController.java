package com.example.itemsservice.controller;

import com.example.itemsservice.models.Item;
import com.example.itemsservice.models.Product;
import com.example.itemsservice.service.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class ItemController {

    @Qualifier("ServiceFeign")
    private final ItemService itemService;

    private final CircuitBreakerFactory cbFactory;

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService, CircuitBreakerFactory cbFactory) {
        this.itemService = itemService;
        this.cbFactory = cbFactory;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> list(@RequestParam(name = "name", required = false) String name,
                                           @RequestHeader(name = "token-request", required = false) String token){
        System.out.println("RequestParam: "+ name);
        System.out.println("RequestHeader: "+ token);
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/items/{id}/amount/{amount}")
    public ResponseEntity<Item> details(@PathVariable("id") Long id, @PathVariable("amount") Integer amount) throws InterruptedException {
        return cbFactory.create("items")
                .run(
                        () -> ResponseEntity.ok(itemService.findById(id, amount)),
                        e -> alternativeMethod(id, amount, e)
                );
    }

    @GetMapping("/items2/{id}/amount/{amount}")
    @CircuitBreaker(name = "items", fallbackMethod = "alternativeMethod")
    public ResponseEntity<Item> details2(@PathVariable("id") Long id, @PathVariable("amount") Integer amount) throws InterruptedException {
        return ResponseEntity.ok(itemService.findById(id, amount));
    }

    @GetMapping("/items3/{id}/amount/{amount}")
    @TimeLimiter(name = "items", fallbackMethod = "alternativeMethod2")
    @CircuitBreaker(name = "items")
    public CompletableFuture<ResponseEntity<Item>> details3(@PathVariable("id") Long id, @PathVariable("amount") Integer amount) throws InterruptedException {
        return CompletableFuture.supplyAsync( () ->  ResponseEntity.ok(itemService.findById(id, amount)) );
    }

    public ResponseEntity<Item> alternativeMethod(Long id, Integer amount, Throwable e){

        logger.error("Error in details --> " + e.getMessage());

        Product product = new Product();
        product.setId(id);
        product.setName("Camara Sony");
        product.setPrice(5000.00);

        Item item = new Item(product, amount);
        return ResponseEntity.ok(item);
    }

    public CompletableFuture<ResponseEntity<Item>> alternativeMethod2(Long id, Integer amount, Throwable e){

        logger.error("Error in details --> " + e.getMessage());

        Product product = new Product();
        product.setId(id);
        product.setName("Camara Sony");
        product.setPrice(5000.00);

        Item item = new Item(product, amount);

        return CompletableFuture.supplyAsync( () -> ResponseEntity.ok(item) );
    }
}
