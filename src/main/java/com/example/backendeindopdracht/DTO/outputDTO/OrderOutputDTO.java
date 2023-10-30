package com.example.backendeindopdracht.DTO.outputDTO;



import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.User;
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
public class OrderOutputDTO {

    private Long id;
    private long invoiceId;
    private List<OrderLine> orderLineIds;
    private String customerName;
    private BigDecimal totalAmount;
    private User user;
    private long userid;

}
