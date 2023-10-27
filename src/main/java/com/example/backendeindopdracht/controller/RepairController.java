package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDTO.RepairInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RepairOutputDTO;
import com.example.backendeindopdracht.model.Product;
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

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping("/repairs")
public class RepairController {

    private final RepairService repairService;
    private final RepairRepository repairRepository;




    //POST
    @PostMapping("/add")
    public ResponseEntity<RepairOutputDTO> createRepair(@RequestBody RepairInputDTO repairInputDTO) {
        RepairOutputDTO addedRepair = repairService.addRepair(repairInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRepair);
    }



    @GetMapping()
    public ResponseEntity<List<RepairOutputDTO>> getAllRepairs(){
        return ResponseEntity.ok().body(repairService.getAllRepairs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairOutputDTO> getRepairById (@PathVariable long id){
        return ResponseEntity.ok().body(repairService.getRepairById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<RepairOutputDTO> updateRepair(@PathVariable Long id, @RequestBody RepairInputDTO repairInputDTO) {
        RepairOutputDTO updatedRepair = repairService.updateRepair(repairInputDTO, id);
        return ResponseEntity.ok(updatedRepair);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Repair> deleteRepair (@PathVariable Long id){
        repairService.deleteRepair(id);
        return ResponseEntity.noContent().build();
    }

}
