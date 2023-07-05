package com.example.backendeindopdracht.DTO.inputDto;

import com.example.backendeindopdracht.model.Product;
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
public class InvoiceInputDTO {

    private Long invoiceNumber;
    private String customerName;
    private String invoiceDate;
    private List<Product> products;
    private BigDecimal totalAmount;
    private String email;
}
