package com.example.backendeindopdracht.DTO.outputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineOutputDto {


    private String productName;
    private int quantity;
    private double unitPrice;


    public OrderLineOutputDto(Long orderId, int quantity, double unitPrice, String productName) {
    }
}
