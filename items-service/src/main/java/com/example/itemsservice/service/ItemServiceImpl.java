package com.example.itemsservice.service;

import com.example.itemsservice.models.Item;
import com.example.itemsservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService{

    private final RestTemplate restClient;

    public ItemServiceImpl(RestTemplate restTemplate) {
        this.restClient = restTemplate;
    }

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays.asList(
                restClient.getForObject("http://localhost:8001/products", Product[].class)
        );
        return products
                .stream()
                .map(x -> new Item(x, 1))
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Product product = restClient.getForObject("http://localhost:8001/products/{id}", Product.class, pathVariables);
        return new Item(product, amount);
    }
}
