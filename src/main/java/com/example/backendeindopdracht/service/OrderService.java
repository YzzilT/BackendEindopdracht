package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.*;
import com.example.backendeindopdracht.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;






    public OrderOutputDTO addOrder (OrderInputDTO orderInputDTO) {

        Order order = transferOrderInputDtoToOrder(orderInputDTO);

        Invoice invoice = invoiceRepository.findById(orderInputDTO.getInvoiceId()).orElse(null);
        order.setInvoice(invoice);

        User user = userRepository.findById(orderInputDTO.getUserid()).orElseThrow(() -> new RecordNotFoundException("User not found with ID: " + orderInputDTO.getUserid()));
        order.setUser(user);

        orderRepository.save(order);

        return transferOrderToOutputDTO(order);

    }



    public List<OrderOutputDTO> getAllOrders(){
        Iterable<Order> orders = orderRepository.findAll();
        List<OrderOutputDTO> orderOutputDTOList = new ArrayList<>();

        for (Order order : orders){
            orderOutputDTOList.add(transferOrderToOutputDTO(order));
        }
        return orderOutputDTOList;
    }



    public OrderOutputDTO getOrderById(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);

        Order order = optionalOrder.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"order not found"));
        return transferOrderToOutputDTO(order);
    }



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



    public Order deleteOrder(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);

        orderRepository.deleteById(id);
        return optionalOrder.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"order not found"));
    }


    public Order transferOrderInputDtoToOrder(OrderInputDTO orderInputDTO){
        Order order = new Order();

        order.setId(orderInputDTO.getId());
        Invoice invoice = invoiceRepository.findById(orderInputDTO.getInvoiceId()).orElse(null);
        order.setInvoice(invoice);
        order.setUser(userRepository.findById(orderInputDTO.getUserid()).orElse(null));


        return order;
    }


    public OrderOutputDTO transferOrderToOutputDTO (Order order){

        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

        orderOutputDTO.setId(order.getId());
        invoiceRepository.findById(orderOutputDTO.getInvoiceId()).ifPresent(invoice -> orderOutputDTO.setInvoiceId(invoice.getInvoiceNumber()));
        orderOutputDTO.setTotalAmount(order.getTotalAmount());

        return orderOutputDTO;
    }
}
