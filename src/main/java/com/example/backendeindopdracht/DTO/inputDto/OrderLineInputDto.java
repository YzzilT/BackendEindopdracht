package com.example.backendeindopdracht.DTO.inputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineInputDto {

    private Long orderId;
    private String productName;
    private int quantity;
    private double unitPrice;
}
