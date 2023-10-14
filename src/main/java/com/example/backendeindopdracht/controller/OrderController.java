package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDTO.OrderInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderOutputDTO;
import com.example.backendeindopdracht.model.Order;
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



    @PostMapping
    public ResponseEntity<OrderOutputDTO> addOrder (@RequestBody OrderInputDTO orderInputDTO){

       OrderOutputDTO orderOutputDTO = orderService.addOrder(orderInputDTO);

       return ResponseEntity.status(HttpStatus.CREATED).body(orderOutputDTO);
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
    @PutMapping()
    public ResponseEntity<OrderOutputDTO> updateOrder (@RequestBody OrderInputDTO order){
        var output = orderService.updateOrder(order,order.getId());
        return ResponseEntity.status(HttpStatus.OK).body(output);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder (@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

}
