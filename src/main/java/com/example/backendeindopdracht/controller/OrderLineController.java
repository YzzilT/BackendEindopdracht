package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
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

    private final OrderLineRepository orderLineRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;

    @GetMapping()
    public ResponseEntity<Iterable<OrderLineOutputDTO>> getAllOrderLines(){
        return ResponseEntity.ok().body(orderLineService.getAllOrderLines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLineOutputDTO> getOrderLineById (@PathVariable long id){
        return ResponseEntity.ok().body(orderLineService.getOrderLineById(id));
    }

    //POST
    @PostMapping()
    public ResponseEntity<OrderLineOutputDTO> addOrderLine (@RequestBody OrderlineInputDTO orderlineInputDTO){
        OrderLineOutputDTO addedOrderLine = orderLineService.addOrderLine(orderlineInputDTO);

        //userrepo.find(jwt)
//        if(user.role.name != "backendmedewerker"){
//            throw NotauthorizedException();
//        }

//        orderLine.setProduct(productRepository.findById(orderLine.getProductid()).get());
//        orderLine.setOrder(orderRepository.findById(orderLine.getOrderid()).get());
//        orderLine = orderLineRepository.save(orderLine);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedOrderLine);
    }

    @PutMapping()
    public ResponseEntity<OrderLineOutputDTO> updateOrderLine (@PathVariable Long id, @Valid @RequestBody OrderlineInputDTO orderlineInputDTO){
        return ResponseEntity.ok().body(orderLineService.updateOrderLine(orderlineInputDTO, id));
//        orderLine = orderLineRepository.save(orderLine);
//        return ResponseEntity.status(HttpStatus.OK).body(orderLine);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderLine> deleteOrderLine(@PathVariable long id){
        var orderLine = orderLineService.deleteOrderLine(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderLine);
//        orderLineRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
    }

}
