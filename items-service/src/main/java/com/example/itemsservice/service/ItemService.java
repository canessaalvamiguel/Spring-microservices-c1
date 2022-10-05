package com.example.itemsservice.service;

import com.example.itemsservice.models.Item;

import java.util.List;

public interface ItemService {
    public List<Item> findAll();
    public Item findById(Long id, Integer amount);
}
