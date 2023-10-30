package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.service.OrderLineService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/orderlines")
public class OrderLineController {


    private final OrderLineService orderLineService;

    @PostMapping()
    public ResponseEntity<OrderLineOutputDTO> addOrderLine (@RequestBody OrderlineInputDTO orderlineInputDTO) throws Exception {

        var out =orderLineService.addOrderLine(orderlineInputDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(out);
    }

    @GetMapping()
    public ResponseEntity<Iterable<OrderLineOutputDTO>> getAllOrderLines(){
        return ResponseEntity.ok().body(orderLineService.getAllOrderLines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLineOutputDTO> getOrderLineById (@PathVariable long id){
        return ResponseEntity.ok().body(orderLineService.getOrderLineById(id));
    }


    @PutMapping("/id")
    public ResponseEntity<OrderLineOutputDTO> updateOrderLine (@PathVariable Long id, @Valid @RequestBody OrderlineInputDTO orderlineInputDTO){
        return ResponseEntity.ok().body(orderLineService.updateOrderLine(orderlineInputDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderLine> deleteOrderLine(@PathVariable long id){
        var orderLine = orderLineService.deleteOrderLine(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(orderLine);

    }

}
