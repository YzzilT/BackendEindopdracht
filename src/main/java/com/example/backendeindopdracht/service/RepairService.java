package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.RepairInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RepairOutputDTO;
import com.example.backendeindopdracht.model.Repair;
import com.example.backendeindopdracht.repository.RepairRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RepairService {

    private final RepairRepository repairRepository;

    //POST
    public RepairOutputDTO addRepair (RepairInputDTO repairInputDTO){
        Repair repair = transferInputDtoRepairToRepair(repairInputDTO);
        repairRepository.save(repair);
        return transferRepairToDTO(repair);
    }




    public Repair transferInputDtoRepairToRepair(RepairInputDTO repairInputDTO){
        Repair repair = new Repair();

        repair.setRepairNumber(repairInputDTO.getRepairNumber());
        repair.setCustomerName(repairInputDTO.getCustomerName());
        repair.setProductName(repairInputDTO.getProductName());
        repair.setProblemDescription(repairInputDTO.getProblemDescription());

        return repair;
    }

    public RepairOutputDTO transferRepairToDTO(Repair repair){
        RepairOutputDTO repairOutputDTO = new RepairOutputDTO();

        repairOutputDTO.setRepairNumber(repair.getRepairNumber());
        repairOutputDTO.setCustomerName(repair.getCustomerName());
        repairOutputDTO.setProductName(repair.getProductName());
        repairOutputDTO.setProblemDescription(repair.getProblemDescription());

        return repairOutputDTO;
    }

}
