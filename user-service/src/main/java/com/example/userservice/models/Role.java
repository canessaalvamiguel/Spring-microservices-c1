package com.example.userservice.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "roles")
@Data
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 30)
    private String name;
}
