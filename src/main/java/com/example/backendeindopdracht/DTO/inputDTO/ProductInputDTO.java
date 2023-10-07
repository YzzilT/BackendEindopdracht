package com.example.backendeindopdracht.DTO.inputDTO;

import com.example.backendeindopdracht.model.ProductType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private ProductType productType;
}
