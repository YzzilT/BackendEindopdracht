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



    //@Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //@Id
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;




    //can't i change/delete this method? i already have a getter?
    public Long getOrderId() {
        return id;
    }



}


