package com.example.backendeindopdracht.DTO.outputDTO;


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
    private Long orderid;
    private Long productid;



}
