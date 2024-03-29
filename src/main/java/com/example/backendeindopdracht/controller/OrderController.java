package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
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



    @PostMapping
    public ResponseEntity<OrderOutputDTO> addOrder (@RequestBody OrderInputDTO orderInputDTO){

       OrderOutputDTO orderOutputDTO = orderService.addOrder(orderInputDTO);


       return ResponseEntity.status(HttpStatus.CREATED).header("Location","/orders/" + orderOutputDTO.getId()).body(orderOutputDTO);
    }



    @GetMapping
    public ResponseEntity<List<OrderOutputDTO>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDTO> getOrderById (@PathVariable Long id){
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderOutputDTO> updateOrder (@PathVariable Long id,@RequestBody OrderInputDTO order){
        var output = orderService.updateOrder(order,id);
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder (@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
