package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.RepairInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RepairOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Repair;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.repository.RepairRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Service
public class RepairService {

    private final RepairRepository repairRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //POST
    public RepairOutputDTO addRepair (RepairInputDTO repairInputDTO){
        Repair repair = transferInputDtoRepairToRepair(repairInputDTO);
        repair.setUser(userRepository.findById(repair.getUserid()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));
        repair.setProduct(productRepository.findById(repair.getProductid()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));
        repairRepository.save(repair);
        return transferRepairToDTO(repair);
    }




    //PUT
    public RepairOutputDTO updateRepair(RepairInputDTO repairInputDTO, Long repairId){
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new RecordNotFoundException("No repair found with id: " + repairId));

        repair.setProblemDescription(repairInputDTO.getProblemDescription());

        if (repairInputDTO.getPicture() != null){
            repair.setPicture(repairInputDTO.getPicture());

        }

        repairRepository.save(repair);
        return transferRepairToDTO(repair);

    }




    public Repair transferInputDtoRepairToRepair(RepairInputDTO repairInputDTO){
        Repair repair = new Repair();

        repair.setRepairNumber(repairInputDTO.getRepairNumber());
        repair.setProblemDescription(repairInputDTO.getProblemDescription());
        repair.setPicture(repairInputDTO.getPicture());

        return repair;
    }

    public RepairOutputDTO transferRepairToDTO(Repair repair){
        RepairOutputDTO repairOutputDTO = new RepairOutputDTO();

        repairOutputDTO.setRepairNumber(repair.getRepairNumber());
        repairOutputDTO.setProblemDescription(repair.getProblemDescription());
        repairOutputDTO.setPicture(repair.getPicture());

        return repairOutputDTO;
    }

}
