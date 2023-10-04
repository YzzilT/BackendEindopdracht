package com.example.backendeindopdracht.DTO.outputDTO;

import com.example.backendeindopdracht.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineOutputDTO {

    private Long id;
    private String productName;
    private int quantity;
    private double unitPrice;
    private Order order;
    private Long orderid;
    private Long productid;


    public OrderLineOutputDTO(Long orderId, int quantity, double unitPrice, String productName) {
    }
}
