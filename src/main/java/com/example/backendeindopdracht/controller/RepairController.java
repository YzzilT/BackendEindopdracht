package com.example.backendeindopdracht.controller;

import com.example.backendeindopdracht.DTO.inputDto.RepairInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.RepairOutputDTO;
import com.example.backendeindopdracht.service.RepairService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/repairs")
public class RepairController {

    private final RepairService repairService;

    //POST
    @PostMapping("/add")
    public ResponseEntity<RepairOutputDTO> addRepair (@RequestBody RepairInputDTO repairInputDTO){
        RepairOutputDTO addedRepair = repairService.addRepair(repairInputDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(addedRepair);
    }
}
