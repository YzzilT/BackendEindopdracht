package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.RepairInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RepairOutputDTO;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Repair;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.repository.RepairRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import com.example.backendeindopdracht.service.RepairService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping("/repairs")
public class RepairController {

    private final RepairService repairService;
    private final RepairRepository repairRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //POST
    @PostMapping("/add")
    public ResponseEntity<Repair> addRepair (@RequestBody Repair repair){
        repair.setUser(userRepository.findById(repair.getUserid()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));
        repair.setProduct(productRepository.findById(repair.getProductid()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));

        repair = repairRepository.save(repair);
        return  ResponseEntity.status(HttpStatus.CREATED).body(repair);
    }

    @PutMapping()
    public ResponseEntity<Repair> updateRepair(@RequestBody Repair repair){
        return addRepair(repair);
    }

    @GetMapping()
    public ResponseEntity<Iterable<Repair>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(repairRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repair> get (@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(repairRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));
    }
}
