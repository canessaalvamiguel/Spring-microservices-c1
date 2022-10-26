package com.example.itemsservice.service;

import com.example.itemsservice.models.Item;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.commonsserver.models.Product;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService{

    private final RestTemplate restClient;

    public ItemServiceImpl(RestTemplate restTemplate) {
        this.restClient = restTemplate;
    }

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays.asList(
                restClient.getForObject("http://products-service/products", Product[].class)
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
        Product product = restClient.getForObject("http://products-service/products/{id}", Product.class, pathVariables);
        return new Item(product, amount);
    }

    @Override
    public Product save(Product product) {
        HttpEntity<Product> body = new HttpEntity<Product>(product);

        ResponseEntity<Product> response = restClient.exchange("http://products-service/products/", HttpMethod.POST, body, Product.class);
        Product productResponse = response.getBody();
        return productResponse;
    }

    @Override
    public Product update(Product product, Long id) {
        HttpEntity<Product> body = new HttpEntity<Product>(product);

        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());

        ResponseEntity<Product> response = restClient.exchange("http://products-service/products/{id}", HttpMethod.PUT, body, Product.class);
        Product productResponse = response.getBody();
        return productResponse;
    }

    @Override
    public void delete(Long id) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", id.toString());

        restClient.delete("http://products-service/products/{id}", uriVariables);
    }
}
