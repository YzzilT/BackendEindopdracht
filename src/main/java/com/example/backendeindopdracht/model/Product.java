package com.example.backendeindopdracht.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    //@NotNull(message = "Product type must be selected")
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    //TODO message doesn't work, fix later


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
    private List<OrderLine> orderLines = new ArrayList<>();



}

