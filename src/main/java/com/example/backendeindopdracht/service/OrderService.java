package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDto.OrderInputDTO;
import com.example.backendeindopdracht.DTO.inputDto.OrderLineInputDto;
import com.example.backendeindopdracht.DTO.outputDto.OrderLineOutputDto;
import com.example.backendeindopdracht.DTO.outputDto.OrderOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;




    //POST
    public OrderOutputDTO addOrder (OrderInputDTO orderInputDTO){
        Order order = transferOrderInputDtoToOrder(orderInputDTO);
        order = orderRepository.save(order);
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


    //DELETE
    public void deleteOrder(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()){
            throw new RecordNotFoundException("No order was found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    /*//Add product to order
    public OrderOutputDTO assignProductToOrder (Long id, Long orderid){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Optional<Product> optionalProduct = productRepository.findById(orderid);
        if (optionalOrder.isEmpty()){
            throw new RecordNotFoundException("No order found with id: " + id);
        }
        if (optionalProduct.isEmpty()){
            throw new RecordNotFoundException("no product found with id: " + id);
        }

        Order order = optionalOrder.get();
        Product product = optionalProduct.get();
        List<Product> productList = order.getProducts();
        productList.add(product);
        order.setProducts(productList);
        orderRepository.save(order);
        return transferOrderToOutputDTO(order);
    }
*/
    public OrderLineOutputDto addOrderLine(Long orderId, OrderLineInputDto orderLineInputDto){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RecordNotFoundException("No order found with id: " + orderId));

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(orderLineInputDto.getQuantity());
        orderLine.setUnitPrice(orderLineInputDto.getUnitPrice());
        orderLine.setProductName(orderLineInputDto.getProductName());

        order.getOrderLines().add(orderLine);

        orderRepository.save(order);

        return new OrderLineOutputDto(orderLine.getOrderId(), orderLine.getQuantity(), orderLine.getUnitPrice(), orderLine.getProductName());
    }

   /* public void addProductToOrder(Order order, Product product){
        order.addProduct(product);
        product.setOrder(order);
        orderRepository.save(order);
    }*/




    public Order transferOrderInputDtoToOrder(OrderInputDTO orderInputDTO){
        Order order = new Order();

        order.setCustomerName(orderInputDTO.getCustomerName());
        order.setUnitPrice(orderInputDTO.getUnitPrice());
        order.setQuantity(orderInputDTO.getQuantity());
        order.setTotalAmount(orderInputDTO.getTotalAmount());

        return order;
    }

    public OrderOutputDTO transferOrderToOutputDTO (Order order){

        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

        orderOutputDTO.setId(order.getId());
        orderOutputDTO.setCustomerName(order.getCustomerName());
        orderOutputDTO.setUnitPrice(order.getUnitPrice());
        orderOutputDTO.setQuantity(order.getQuantity());
        orderOutputDTO.setTotalAmount(order.getTotalAmount());

        return orderOutputDTO;


    }
}
