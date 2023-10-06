package com.example.backendeindopdracht.DTO.inputDTO;

import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.ProductType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInputDTO {

    private Long id;
    @NotBlank
    private String name;
    @Min(value = 0 , message = "The product cant be negative in price")
    private double price;
    private int originalStock;
    private int currentStock;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ProductType productType;
    private Order order;
    private List<OrderLine> orderLines = new ArrayList<>();
}
