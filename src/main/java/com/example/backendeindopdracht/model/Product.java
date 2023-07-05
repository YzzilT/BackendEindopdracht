package com.example.backendeindopdracht.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int originalStock;
    private int currentStock;
    private String description;
    @NotNull(message = "Product type must be selected")
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    //TODO message doesn't work, fix later
    //TODO where does order_id in pgAdmin come from?

    @ManyToOne
    @JoinColumn
    private Order order;

}

