package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDto.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.OrderOutputDTO;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    //POST
    @PostMapping("/add")
    public ResponseEntity<OrderOutputDTO> addOrder (@RequestBody OrderInputDTO orderInputDTO){
        OrderOutputDTO addedOrder = orderService.addOrder(orderInputDTO);
        //TODO addedOrder.updateStock();
        //addedOrder.calculateTotalPrice();
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrder);
    }

    private final OrderLineRepository orderLineRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/addOrderLine/{orderid}/{product}")
    public ResponseEntity<OrderLine> addOrder (@RequestBody OrderLine orderline,@PathVariable int orderid){
        Order order = orderRepository.findById((long) orderid).get();
        orderline.setOrder(order);
        OrderLine orderlineout = orderLineRepository.save(orderline);
        orderlineout.setOrder(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderlineout);
    }


    //GET ALL
    @GetMapping
    public ResponseEntity<List<OrderOutputDTO>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDTO> getOrderById (@PathVariable Long id){
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }

    //PUT



    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder (@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }


   /* public void calculateStock() {
        Order o = null;
        o.quantity = 0;
        o.setQuantity(0);
    }*/



}
