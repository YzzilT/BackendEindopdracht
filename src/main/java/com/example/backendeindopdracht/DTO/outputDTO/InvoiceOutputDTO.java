package com.example.backendeindopdracht.DTO.outputDTO;

import com.example.backendeindopdracht.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOutputDTO {

    private Long invoiceNumber;
    private Order order;
    private String customerName;
    private String invoiceDate;
    //private List<Product> products;
    private BigDecimal totalAmount;
    private String email;
    private long orderId;
}
