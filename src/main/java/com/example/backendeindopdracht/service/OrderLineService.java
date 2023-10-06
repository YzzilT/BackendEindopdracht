package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public List<Long> extractIdsFromOrderLines(List<OrderLine> orderLines) {
        List<Long> orderLineIds = new ArrayList<>();

        for (OrderLine orderLine : orderLines) {
            Long orderLineId = orderLine.getId();
            orderLineIds.add(orderLineId);
        }

        return orderLineIds;

    }


    //POST
//    public OrderLineOutputDTO addOrderLine (Long productId, int quantity){
//
//        Optional<Product> optionalProduct = productRepository.findById(productId);
//        if (optionalProduct.isEmpty()){
//            throw new RecordNotFoundException("No product found with id: " + productId);
//        }
//
//        Product product = optionalProduct.get();
//        OrderLine orderLine = new OrderLine();
//
//        orderLine.setProduct(product);
//        orderLine.setQuantity(quantity);
//
//        BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
//        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));
//
//        orderLine.setTotalAmount(totalAmount);
//
//        orderLine = orderLineRepository.save(orderLine);
//
//
//        OrderLineOutputDTO orderLineOutputDTO = new OrderLineOutputDTO();
//        orderLineOutputDTO.setId(orderLine.getId());
//        orderLineOutputDTO.setQuantity(orderLine.getQuantity());
//        orderLineOutputDTO.setTotalAmount(orderLine.getTotalAmount());
//
//        return orderLineOutputDTO;
//
//        }


    //POST
//    public OrderLineOutputDTO addProductToOrderLine(Long productId, int quantity) {
//
//        Optional<OrderLine> existingOrderLine = orderLineRepository.findByProduct(productId);
//
//        if (existingOrderLine.isPresent()) {
//            OrderLine orderLine = existingOrderLine.get();
//            int newQuantity = orderLine.getQuantity() + quantity;
//            orderLine.setQuantity(newQuantity);
//            BigDecimal unitPrice = BigDecimal.valueOf(orderLine.getProduct().getPrice());
//            BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(newQuantity));
//            orderLine.setTotalAmount(totalAmount);
//            orderLineRepository.save(orderLine);
//
//            return transferOrderLineToDTO(orderLine);
//        } else {
//
//            Optional<Product> optionalProduct = productRepository.findById(productId);
//            if (optionalProduct.isEmpty()) {
//                throw new RecordNotFoundException("No product found with id: " + productId);
//            }
//            Product product = optionalProduct.get();
//            OrderLine orderLine = new OrderLine();
//
//            orderLine.setProduct(product);
//            orderLine.setQuantity(quantity);
//
//            BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
//            BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(quantity));
//
//            orderLine.setTotalAmount(totalAmount);
//
//            orderLine = orderLineRepository.save(orderLine);
//
//            return transferOrderLineToDTO(orderLine);
//
//        }
//
//    }



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
            throw new RecordNotFoundException("No orderline was found with id: " + id);
        }
        orderLineRepository.deleteById(id);
        return optionalOrderLine.get();
    }



    public OrderLine transferInputDtoToOrderLine (OrderlineInputDTO orderlineInputDTO){

        OrderLine orderLine = new OrderLine();

        orderLine.setId(orderlineInputDTO.getId());
        orderLine.setQuantity(orderlineInputDTO.getQuantity());
        orderLine.setProduct(productRepository.findById(orderlineInputDTO.getProductId()).orElse(null));
        orderLine.setOrder(orderRepository.findById(orderlineInputDTO.getOrderId()).orElse(null));

        return orderLine;

        // TODO: 10/4/2023 how to add bigdecimal, order and product?




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

    // TODO: 10/4/2023 how to add bigdecimal, order and product?

}
