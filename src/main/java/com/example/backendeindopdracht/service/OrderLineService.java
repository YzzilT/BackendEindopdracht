package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;


    public OrderLineOutputDTO addOrderLine (OrderlineInputDTO orderlineInputDTO) throws Exception {

        List<OrderLine> orderLineList = orderLineRepository.findByProduct_Id(orderlineInputDTO.getProductId());

        for (OrderLine orderLine : orderLineList) {
            if (orderLine.getOrder().getId().equals(orderlineInputDTO.getOrderId())) {
                int mergedQuantity = orderLine.getQuantity() + orderlineInputDTO.getQuantity();
                orderLine.setQuantity(mergedQuantity);
                orderLineRepository.save(orderLine);
                return transferOrderLineToDTO(orderLine);
            }
        }

        return createNewOrderLine(orderlineInputDTO);
    }

    private OrderLineOutputDTO createNewOrderLine(OrderlineInputDTO orderlineInputDTO) throws Exception {
        var orderLine = transferInputDtoToOrderLine(orderlineInputDTO);
        Long orderId = orderlineInputDTO.getOrderId();

        if (orderId == null) {
            String errorMessage = "Order ID cannot be null.";
            throw new RecordNotFoundException(errorMessage);
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            String errorMessage = "Order with ID " + orderlineInputDTO.getOrderId() + " does not exist.";
            throw new RecordNotFoundException(errorMessage);
        }

        productService.updateStockWhenBuyingProduct(orderlineInputDTO.getProductId(),1);

        Order order = optionalOrder.get();
        orderLine.setOrder(order);
        orderLine.setProduct(productRepository.findById(orderlineInputDTO.getProductId()).orElse(null));
        orderLine = orderLineRepository.save(orderLine);


        return transferOrderLineToDTO(orderLine);
    }


    //GET ALL
    public List<OrderLineOutputDTO> getAllOrderLines(){

        Iterable<OrderLine> orderLines = orderLineRepository.findAll();
        List<OrderLineOutputDTO> orderLineOutputDTOList = new ArrayList<>();


        for (OrderLine orderLine : orderLines){
            orderLineOutputDTOList.add(transferOrderLineToDTO(orderLine));
        }
        return orderLineOutputDTOList;
    }

    //GET BY ID
    public OrderLineOutputDTO getOrderLineById(Long id){
        Optional<OrderLine> optionalOrderLine = orderLineRepository.findById(id);
        if (optionalOrderLine.isEmpty()){
            throw new RecordNotFoundException("No orderline found with id: " + id);
        }
        OrderLine orderLine = optionalOrderLine.get();
        return transferOrderLineToDTO(orderLine);
    }


    //PUT
    public OrderLineOutputDTO updateOrderLine (OrderlineInputDTO orderlineInputDTO, Long id){
        Optional<OrderLine> optionalOrderLine = orderLineRepository.findById(id);
        if (optionalOrderLine.isEmpty()){
            throw new RecordNotFoundException("No orderline found with id " + id);
        } else {
            OrderLine updateOrderLine = transferInputDtoToOrderLine(orderlineInputDTO);
            updateOrderLine.setId(id);
            OrderLine updatedOrderLine = orderLineRepository.save(updateOrderLine);

            return transferOrderLineToDTO(updatedOrderLine);
        }
    }

    //DELETE
    public OrderLine deleteOrderLine (Long id){
        Optional<OrderLine> optionalOrderLine = orderLineRepository.findById(id);
        if (optionalOrderLine.isEmpty()){
            throw new RecordNotFoundException("No orderline found with id: " + id);
        }
        orderLineRepository.deleteById(id);
        return optionalOrderLine.get();
    }





    public OrderLine transferInputDtoToOrderLine (OrderlineInputDTO orderlineInputDTO){

        OrderLine orderLine = new OrderLine();

        orderLine.setId(orderlineInputDTO.getId());
        orderLine.setQuantity(orderlineInputDTO.getQuantity());
        orderLine.setTotalAmount(orderlineInputDTO.getTotalAmount());
        orderLine.setProduct(productRepository.findById(orderlineInputDTO.getProductId()).orElse(null));
        orderLine.setOrder(orderRepository.findById(orderlineInputDTO.getOrderId()).orElse(null));

        return orderLine;
    }



    public OrderLineOutputDTO transferOrderLineToDTO (OrderLine orderLine){

        OrderLineOutputDTO orderLineOutputDTO = new OrderLineOutputDTO();

        orderLineOutputDTO.setId(orderLine.getId());
        orderLineOutputDTO.setQuantity(orderLine.getQuantity());
        orderLineOutputDTO.setTotalAmount(orderLine.getTotalAmount());
        orderLineOutputDTO.setOrder(orderLine.getOrder());
        orderLineOutputDTO.setProduct(orderLine.getProduct());

        return orderLineOutputDTO;
    }
}
