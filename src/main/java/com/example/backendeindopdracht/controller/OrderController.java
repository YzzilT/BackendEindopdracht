package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.UserRepository;
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
    private final OrderLineRepository orderLineRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    @PostMapping("")
    public ResponseEntity<Order> addOrder (@RequestBody Order order){

        var user = userRepository.findById(order.getUserid()).get();
        order.setUser(user);
        order = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }


//    @PostMapping("/addOrderLine/{orderid}/{product}")
//    public ResponseEntity<OrderLine> addOrder (@RequestBody OrderLine orderline,@PathVariable int orderid){
//        Order order = orderRepository.findById((long) orderid).get();
//        orderline.setOrder(order);
//        OrderLine orderlineout = orderLineRepository.save(orderline);
//        orderlineout.setOrder(null);
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderlineout);
//    }


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
    @PutMapping()
    public ResponseEntity<Order> updateOrder (@RequestBody Order order){
        order = orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

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
