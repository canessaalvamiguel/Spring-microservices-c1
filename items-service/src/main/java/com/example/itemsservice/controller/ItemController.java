package com.example.itemsservice.controller;

import com.example.itemsservice.models.Item;
import com.example.itemsservice.service.ItemService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Qualifier("ServiceFeign")
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> list(){
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping("/items/{id}/amount/{amount}")
    public ResponseEntity<Item> details(@PathVariable("id") Long id, @PathVariable("amount") Integer amount){
        return ResponseEntity.ok(itemService.findById(id, amount));
    }
}
