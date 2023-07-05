package com.example.backendeindopdracht.DTO.outputDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOutputDTO {

    private Long invoiceNumber;
    private String customerName;
    private String invoiceDate;
    //private List<Product> products;
    private BigDecimal totalAmount;
    private String email;
}
