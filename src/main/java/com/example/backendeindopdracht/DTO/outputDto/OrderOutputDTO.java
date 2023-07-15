package com.example.backendeindopdracht.DTO.outputDto;


import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.model.OrderLine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutputDTO {

    private Long id;
    private Invoice invoice;
    private List<OrderLine> orderLines;
    private String customerName;
    private double unitPrice;
    private int quantity;
    private BigDecimal totalAmount;
}
