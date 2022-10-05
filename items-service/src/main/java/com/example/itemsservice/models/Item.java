package com.example.itemsservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {

    private Product product;
    private Integer amount;

    public Double getTotal(){
        return this.product.getPrice() * this.amount.doubleValue();
    }
}
