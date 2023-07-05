package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDto.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.OrderOutputDTO;
import com.example.backendeindopdracht.model.Order;
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

    //GET ALL
    @GetMapping
    public ResponseEntity<List<OrderOutputDTO>> getAllProducts(){
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
