package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.OrderLineRepository;
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


    //POST
    public OrderLineOutputDTO addOrderLine (OrderlineInputDTO orderlineInputDTO){
        OrderLine orderLine = transferInputDtoToOrderLine(orderlineInputDTO);
        Long productId = orderlineInputDTO.getProductid();
        if (productId == null){
            throw new IllegalArgumentException("Product ID is required");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            throw new RecordNotFoundException("No product found with id: " + orderlineInputDTO.getProductid());
        } else {

            orderLine.setProduct(optionalProduct.get());
            orderLineRepository.save(orderLine);

            return transferOrderLineToDTO(orderLine);

        }
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
            throw new RecordNotFoundException("No orderline was found with id: " + id);
        }
        orderLineRepository.deleteById(id);
        return optionalOrderLine.get();
    }



    public OrderLine transferInputDtoToOrderLine (OrderlineInputDTO orderlineInputDTO){

        OrderLine orderLine = new OrderLine();

        orderLine.setId(orderlineInputDTO.getId());

        orderLine.setQuantity(orderlineInputDTO.getQuantity());
        orderLine.setUnitPrice(orderlineInputDTO.getUnitPrice());
        //hoe order??

        orderLine.setProductid(orderlineInputDTO.getProductid());
        Optional<Product> optionalProduct = productRepository.findById(orderLine.getProductid());

        if (optionalProduct.isEmpty()){
            throw new RecordNotFoundException("no product found with id: " + orderLine.getProductid());

        }
        else {
            orderLine.setProduct(optionalProduct.get());
        }
        return orderLine;

        // TODO: 9/30/2023  productId nog toevoegen


    }

    public OrderLineOutputDTO transferOrderLineToDTO (OrderLine orderLine){

        OrderLineOutputDTO orderLineOutputDTO = new OrderLineOutputDTO();

        orderLineOutputDTO.setId(orderLine.getId());
        orderLineOutputDTO.setProductName(orderLine.getProductName());
        orderLineOutputDTO.setQuantity(orderLine.getQuantity());
        orderLineOutputDTO.setUnitPrice(orderLine.getUnitPrice());
        //order??
        if (orderLine.getProduct() != null){
            orderLineOutputDTO.setProductid(orderLine.getProduct().getId());
        } else {
            orderLineOutputDTO.setProductid(null);
        }
        return orderLineOutputDTO;
    }

    // TODO: 9/30/2023 orderId toevoegen

}
