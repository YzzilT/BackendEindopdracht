package com.example.backendeindopdracht.DTO.inputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepairInputDTO {

    private Long repairNumber;
    private String customerName;
    private String productName;
    private String problemDescription;
}
