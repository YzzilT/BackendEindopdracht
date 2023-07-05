package com.example.backendeindopdracht.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Invoice invoice;
    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<Product> products;
    private String customerName;
    private double unitPrice;
    private double quantity;
    private BigDecimal totalAmount;

    /*public void setQuantity(double q) {
        if q < 0 {

        }
        this.quantity = q;
    }



public void updateStock() {
    for (products) {

    }
}



    public BigDecimal calculateTotalPrice() {
        for (Product product : products) {
            totalAmount = totalAmount + products.unitPrice;
        }
        return totalAmount;
    }




*/
}