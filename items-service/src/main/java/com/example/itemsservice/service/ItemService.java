package com.example.itemsservice.service;

import com.example.itemsservice.models.Item;
import com.example.itemsservice.models.Product;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Long id, Integer amount);
    public Product save(Product product);
    public Product update(Product product, Long id);
    public void delete(Long id);
}
