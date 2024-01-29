package com.example.backendeindopdracht.DTO.outputDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepairOutputDTO {

    private Long repairNumber;
    private Long productId;
    private String problemDescription;
    private byte[] picture;
    private LocalDate submissionDate;
    private Long userId;
}
