package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;




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
    public OrderLineOutputDTO addOrderLine(Long orderId, OrderlineInputDTO orderLineInputDto){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RecordNotFoundException("No order found with id: " + orderId));

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantity(orderLineInputDto.getQuantity());
        orderLine.setUnitPrice(orderLineInputDto.getUnitPrice());
        orderLine.setProductName(orderLineInputDto.getProductName());

        order.getOrderLines().add(orderLine);

        orderRepository.save(order);

        return new OrderLineOutputDTO(orderLine.getId(), orderLine.getQuantity(), orderLine.getUnitPrice(), orderLine.getProductName());
    }

   /* public void addProductToOrder(Order order, Product product){
        order.addProduct(product);
        product.setOrder(order);
        orderRepository.save(order);
    }*/




    public Order transferOrderInputDtoToOrder(OrderInputDTO orderInputDTO){
        Order order = new Order();

        order.setId(orderInputDTO.getId());
        order.setInvoice(orderInputDTO.getInvoice());
        order.setOrderLines(orderInputDTO.getOrderLines());
        order.setCustomerName(orderInputDTO.getCustomerName());
        order.setTotalAmount(orderInputDTO.getTotalAmount());
        order.setUserid(orderInputDTO.getUserId());
        return order;
    }

    public OrderOutputDTO transferOrderToOutputDTO (Order order){

        OrderOutputDTO orderOutputDTO = new OrderOutputDTO();

        orderOutputDTO.setId(order.getId());
        orderOutputDTO.setInvoice(order.getInvoice());
        orderOutputDTO.setOrderLines(order.getOrderLines());
        orderOutputDTO.setCustomerName(order.getCustomerName());
        orderOutputDTO.setTotalAmount(order.getTotalAmount());
        orderOutputDTO.setUserid(order.getUserid());

        return orderOutputDTO;


    }
}
