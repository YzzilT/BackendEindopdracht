package com.example.backendeindopdracht.DTO.inputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInputDTO {

    private Long id;
    private String productName;
    private String customerName;
    private double unitPrice;
    private double quantity;
    private BigDecimal totalAmount;
}
