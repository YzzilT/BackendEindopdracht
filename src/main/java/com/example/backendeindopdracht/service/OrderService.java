package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDto.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.OrderOutputDTO;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;


    //POST
    public OrderOutputDTO addOrder (OrderInputDTO orderInputDTO){
        Order order = transferOrderInputDtoToOrder(orderInputDTO);
        orderRepository.save(order);
        return transferOrderToOutputDTO(order);

    }




    public Order transferOrderInputDtoToOrder(OrderInputDTO orderInputDTO){
        Order order = new Order();

        order.setProductName(orderInputDTO.getProductName());
        order.setCustomerName(orderInputDTO.getCustomerName());
        order.setUnitPrice(orderInputDTO.getUnitPrice());
        order.setQuantity(orderInputDTO.getQuantity());
        order.setTotalAmount(orderInputDTO.getTotalAmount());

        return order;
    }

    public OrderOutputDTO transferOrderToOutputDTO (Order order){

        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

        orderOutputDTO.setProductName(order.getProductName());
        orderOutputDTO.setCustomerName(order.getCustomerName());
        orderOutputDTO.setUnitPrice(order.getUnitPrice());
        orderOutputDTO.setQuantity(order.getQuantity());
        orderOutputDTO.setTotalAmount(order.getTotalAmount());

        return orderOutputDTO;


    }
}
