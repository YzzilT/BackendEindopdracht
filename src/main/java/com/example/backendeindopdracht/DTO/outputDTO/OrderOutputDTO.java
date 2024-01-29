package com.example.backendeindopdracht.DTO.outputDTO;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutputDTO {

    private Long id;
    private long invoiceId;
    private BigDecimal totalAmount;
    private long userid;

}
