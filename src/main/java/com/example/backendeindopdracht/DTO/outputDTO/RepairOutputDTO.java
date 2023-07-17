package com.example.backendeindopdracht.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepairOutputDTO {

    private Long repairNumber;
    private String customerName;
    private String productName;
    private String problemDescription;
}
