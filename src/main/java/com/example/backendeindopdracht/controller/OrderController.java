package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDto.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.OrderOutputDTO;
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





}
