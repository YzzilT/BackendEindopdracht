package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.RepairInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RepairOutputDTO;
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
    public ResponseEntity<RepairOutputDTO> createRepair(@RequestBody RepairInputDTO repairInputDTO) {
        RepairOutputDTO addedRepair = repairService.addRepair(repairInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRepair);
    }



    @GetMapping()
    public ResponseEntity<Iterable<Repair>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(repairRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Repair> get (@PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(repairRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RepairOutputDTO> updateRepair(@PathVariable Long id, @RequestBody RepairInputDTO repairInputDTO) {
        RepairOutputDTO updatedRepair = repairService.updateRepair(repairInputDTO, id);
        return ResponseEntity.ok(updatedRepair);
    }

}
