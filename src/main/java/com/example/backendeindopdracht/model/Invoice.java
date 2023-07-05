package com.example.backendeindopdracht.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {

    @Id
    private String email;
    @OneToOne(mappedBy = "invoice")
    @JsonIgnore
    private Order order;
    private Long invoiceNumber;
    private String customerName;
    private String invoiceDate;
    private BigDecimal totalAmount;

}