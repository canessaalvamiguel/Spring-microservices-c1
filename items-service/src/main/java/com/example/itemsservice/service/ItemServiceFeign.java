package com.example.itemsservice.service;

import com.example.itemsservice.clients.ProductClientRest;
import com.example.itemsservice.models.Item;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.example.commonsserver.models.Product;
import java.util.List;
import java.util.stream.Collectors;

@Service("ServiceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

    private final ProductClientRest clientFeign;

    public ItemServiceFeign(ProductClientRest clientFeign) {
        this.clientFeign = clientFeign;
    }

    @Override
    public List<Item> findAll() {
        return clientFeign
                .list()
                .stream()
                .map(x -> new Item(x, 1))
                .collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        return new Item(clientFeign.details(id), amount);
    }

    @Override
    public Product save(Product product) {
        return clientFeign.create(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return clientFeign.update(product, id);
    }

    @Override
    public void delete(Long id) {
        clientFeign.delete(id);
    }
}
