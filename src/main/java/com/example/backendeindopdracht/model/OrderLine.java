package com.example.backendeindopdracht.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int quantity;
    private double unitPrice;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Long orderid;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Long productid;


}


