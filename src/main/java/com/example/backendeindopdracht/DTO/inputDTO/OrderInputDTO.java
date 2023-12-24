package com.example.backendeindopdracht.DTO.inputDTO;


import com.example.backendeindopdracht.model.OrderLine;
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
public class OrderInputDTO {

    private Long id;
    private long invoiceId;
    private long userid;




}
