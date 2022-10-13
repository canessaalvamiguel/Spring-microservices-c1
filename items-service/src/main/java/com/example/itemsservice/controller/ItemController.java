package com.example.itemsservice.controller;

import com.example.itemsservice.models.Item;
import com.example.itemsservice.models.Product;
import com.example.itemsservice.service.ItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Qualifier("ServiceFeign")
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> list(@RequestParam(name = "name") String name, @RequestHeader(name = "token-request") String token){
        System.out.println("RequestParam: "+ name);
        System.out.println("RequestHeader: "+ token);
        return ResponseEntity.ok(itemService.findAll());
    }
    @HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/items/{id}/amount/{amount}")
    public ResponseEntity<Item> details(@PathVariable("id") Long id, @PathVariable("amount") Integer amount){
        return ResponseEntity.ok(itemService.findById(id, amount));
    }

    public ResponseEntity<Item> alternativeMethod(Long id, Integer amount){

        Product product = new Product();
        product.setId(id);
        product.setName("Camara Sony");
        product.setPrice(5000.00);

        Item item = new Item(product, amount);
        return ResponseEntity.ok(item);
    }
}
