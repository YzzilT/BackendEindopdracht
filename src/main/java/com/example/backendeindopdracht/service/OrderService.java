package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.*;
import com.example.backendeindopdracht.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;





    //POST
    public OrderOutputDTO addOrder (OrderInputDTO orderInputDTO) {

        Order order = transferOrderInputDtoToOrder(orderInputDTO);

        Invoice invoice = invoiceRepository.findById(orderInputDTO.getInvoiceId()).orElse(null);
        order.setInvoice(invoice);

//        order.setInvoice(invoiceRepository.findById(orderInputDTO.getInvoiceId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find invoice")));
//        order.setUser(userRepository.findById(orderInputDTO.getUserid()).orElse(null));
        User user = userRepository.findById(orderInputDTO.getUserid()).orElseThrow(() -> new RecordNotFoundException("User not found with ID: " + orderInputDTO.getUserid()));
        order.setUser(user);

        orderRepository.save(order);

        return transferOrderToOutputDTO(order);

    }


    //GET ALL
    public List<OrderOutputDTO> getAllOrders(){
        Iterable<Order> orders = orderRepository.findAll();
        List<OrderOutputDTO> orderOutputDTOList = new ArrayList<>();

        for (Order order : orders){
            orderOutputDTOList.add(transferOrderToOutputDTO(order));
        }
        return orderOutputDTOList;
    }


    //GET BY ID
    public OrderOutputDTO getOrderById(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()){
            throw new RecordNotFoundException("No order found with id: " + id);
        }
        Order order = optionalOrder.get();
        return transferOrderToOutputDTO(order);
    }


    //PUT
    public OrderOutputDTO updateOrder (OrderInputDTO orderInputDTO, Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()){
            throw new RecordNotFoundException("No order found with id " + id);
        } else {
            Order updateOrder = transferOrderInputDtoToOrder(orderInputDTO);
            updateOrder.setId(id);
            Order updatedOrder = orderRepository.save(updateOrder);

            return transferOrderToOutputDTO(updatedOrder);
        }
    }


    //DELETE
    public Order deleteOrder(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()){
            throw new RecordNotFoundException("No order was found with id: " + id);
        }
        orderRepository.deleteById(id);
        return optionalOrder.get();
    }


    public Order transferOrderInputDtoToOrder(OrderInputDTO orderInputDTO){
        Order order = new Order();

        order.setId(orderInputDTO.getId());
//        order.setInvoice(invoiceRepository.findById(orderInputDTO.getInvoiceId()).get());
        Invoice invoice = invoiceRepository.findById(orderInputDTO.getInvoiceId()).orElse(null);
        order.setInvoice(invoice);
        order.setCustomerName(orderInputDTO.getCustomerName());
        order.setTotalAmount(orderInputDTO.getTotalAmount());
        order.setOrderLineIds(orderInputDTO.getOrderLineIds());
        order.setUser(userRepository.findById(orderInputDTO.getUserid()).orElse(null));

        // TODO: 10/5/2023 how to set the user?

        return order;
    }

    public OrderOutputDTO transferOrderToOutputDTO (Order order){

        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

        orderOutputDTO.setId(order.getId());
        Invoice invoice = invoiceRepository.findById(orderOutputDTO.getInvoiceId()).orElse(null);
//        orderOutputDTO.setInvoice(order.getInvoice());
        orderOutputDTO.setCustomerName(order.getCustomerName());
        orderOutputDTO.setTotalAmount(order.getTotalAmount());
        orderOutputDTO.setOrderLineIds(order.getOrderLineIds());
        orderOutputDTO.setUser(order.getUser());
        // TODO: 10/5/2023 how to set the user?

        return orderOutputDTO;


    }
}
