package com.example.backendeindopdracht.DTO.inputDTO;

import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.User;
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
public class OrderInputDTO {

    private Long id;
    private Invoice invoice;
    private List<OrderLine> orderLines;
    private String customerName;
    private BigDecimal totalAmount;
    private Long userId;
}
