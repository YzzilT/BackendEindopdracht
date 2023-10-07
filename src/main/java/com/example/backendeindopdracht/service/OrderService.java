package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.*;
import com.example.backendeindopdracht.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderLineRepository orderLineRepository;




    //POST
    public OrderOutputDTO addOrder (OrderInputDTO orderInputDTO) {
        Order order = transferOrderInputDtoToOrder(orderInputDTO);
        order.setInvoice(invoiceRepository.findById(orderInputDTO.getInvoiceId()).get());
        order.setUser(userRepository.findById(orderInputDTO.getUserid()).get());

//        List<OrderLine> orderLineIds = orderInputDTO.getOrderLineIds();
//
//        List<Long> extractedIds = extractIdsFromOrderLines(orderLineIds);
//
//        if (orderLineIds == null || orderLineIds.isEmpty()) {
//            throw new IllegalArgumentException("orderlines are required");
//        }
//
//
//
//        for (Long orderLineId : extractedIds) {
//            addOrderLineToOrder(order, orderLineId);
//        }

        orderRepository.save(order);

        return transferOrderToOutputDTO(order);

    }

    //POST
    public OrderOutputDTO addOrderLineToOrder(Order order, Long orderline_id){

        Optional<OrderLine> optionalOrderLine = orderLineRepository.findById(orderline_id);
        if (optionalOrderLine.isEmpty()){
            throw new RecordNotFoundException("No orderline found with id: " + orderline_id);
        }

        OrderLine orderLine = optionalOrderLine.get();
        //orderline toevoegen aan order
        order.getOrderLineIds().add(orderLine);
        //order opslaan
        orderRepository.save(order);

        return transferOrderToOutputDTO(order);
    }

    public List<Long> extractIdsFromOrderLines(List<OrderLine> orderLines) {
        List<Long> orderLineIds = new ArrayList<>();

        for (OrderLine orderLine : orderLines) {
            Long orderLineId = orderLine.getId();
            orderLineIds.add(orderLineId);
        }
        for (Long id : orderLineIds) {
            System.out.println("OrderLine ID: " + id);
        }

        return orderLineIds;

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

            return transferOrderToOutputDTO(updateOrder);
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
        order.setInvoice(invoiceRepository.findById(orderInputDTO.getInvoiceId()).get());
        order.setCustomerName(orderInputDTO.getCustomerName());
        order.setTotalAmount(orderInputDTO.getTotalAmount());
        order.setOrderLineIds(orderInputDTO.getOrderLineIds());
        // TODO: 10/5/2023 how to set the user and orderlinelist?

        return order;
    }

    public OrderOutputDTO transferOrderToOutputDTO (Order order){

        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

        orderOutputDTO.setId(order.getId());
        orderOutputDTO.setInvoice(order.getInvoice());
        orderOutputDTO.setCustomerName(order.getCustomerName());
        orderOutputDTO.setTotalAmount(order.getTotalAmount());
        orderOutputDTO.setOrderLineIds(order.getOrderLineIds());
        // TODO: 10/5/2023 how to set the user?

        return orderOutputDTO;


    }
}
