package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Role;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/roles")
public class OrderLineController {

    private final OrderLineRepository orderLineRepository;

    @GetMapping()
    public ResponseEntity<Iterable<OrderLine>> getAllOrderLines(){
        return ResponseEntity.status(HttpStatus.OK).body(orderLineRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLine> getOrderLine (@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderLineRepository.findById(id).get());
    }

    //POST
    @PostMapping()
    public ResponseEntity<OrderLine> addOrderLine (@RequestBody OrderLine orderLine){
        orderLine = orderLineRepository.save(orderLine);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderLine);
    }

    @PutMapping()
    public ResponseEntity<OrderLine> updateOrderLine (@RequestBody OrderLine orderLine){
        orderLine = orderLineRepository.save(orderLine);
        return ResponseEntity.status(HttpStatus.OK).body(orderLine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderLine> deleteOrderLine(@PathVariable long id){
        orderLineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
