package com.example.productsservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @Transient
    private Integer port;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private static final long serialVersionUID = 1L;
}
