package com.example.backendeindopdracht.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineOutputDTO {


    private String productName;
    private int quantity;
    private double unitPrice;


    public OrderLineOutputDTO(Long orderId, int quantity, double unitPrice, String productName) {
    }
}
