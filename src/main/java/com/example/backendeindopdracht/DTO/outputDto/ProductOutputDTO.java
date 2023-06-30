package com.example.backendeindopdracht.DTO.outputDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOutputDTO {

    private Long id;
    private String name;
    private double price;
    private int originalStock;
    private int currentStock;
    private String description;
}
