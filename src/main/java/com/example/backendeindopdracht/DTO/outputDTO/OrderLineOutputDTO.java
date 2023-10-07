package com.example.backendeindopdracht.DTO.outputDTO;

import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineOutputDTO {

    private Long id;
    private int quantity;
    private BigDecimal totalAmount;
    private Order order;
    private Product product;



}
