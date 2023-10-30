package com.example.backendeindopdracht.DTO.inputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderlineInputDTO {

    private Long id;
    private int quantity;
    private BigDecimal totalAmount;
    private Long productId;
    private Long orderId;



}
