package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.RepairInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.RepairOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.model.Repair;
import com.example.backendeindopdracht.model.User;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.repository.RepairRepository;
import com.example.backendeindopdracht.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Service
public class RepairService {

    private final RepairRepository repairRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public RepairOutputDTO addRepair (RepairInputDTO repairInputDTO){
        Repair repair = transferInputDtoRepairToRepair(repairInputDTO);
        repair.setUser(userRepository.findById(repair.getUser().getId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find user")));
        repair.setProduct(productRepository.findById(repair.getProduct().getId()).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find product")));
        repairRepository.save(repair);
        return transferRepairToDTO(repair);
    }


    public List<RepairOutputDTO> getAllRepairs() {
        Iterable<Repair> repairs = repairRepository.findAll();
        List<RepairOutputDTO> repairOutputDTOList = new ArrayList<>();

        for (Repair repair : repairs) {
            repairOutputDTOList.add(transferRepairToDTO(repair));
        }
        return repairOutputDTOList;
    }


    public RepairOutputDTO getRepairById(Long id) {
        Optional<Repair> optionalRepair = repairRepository.findById(id);

        Repair repair = optionalRepair.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"repair not found"));
        return transferRepairToDTO(repair);
    }



    public RepairOutputDTO updateRepair(RepairInputDTO repairInputDTO, Long repairId){
        Repair repair = repairRepository.findById(repairId)
                .orElseThrow(() -> new RecordNotFoundException("No repair found with id: " + repairId));

        repair.setProblemDescription(repairInputDTO.getProblemDescription());

        repairRepository.save(repair);
        return transferRepairToDTO(repair);

    }

    public void deleteRepair(Long id) {
        Optional<Repair> optionalRepair = repairRepository.findById(id);
        if (optionalRepair.isEmpty()) {
            throw new RecordNotFoundException("No repair found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<RepairOutputDTO> getRepairsSubmittedOverAWeekAgo() {
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

        Iterable<Repair> repairs = repairRepository.findAll();
        List<RepairOutputDTO> repairOutputDTOList = new ArrayList<>();

        for (Repair repair : repairs) {
            LocalDate submissionDate = repair.getSubmissionDate();

            if (submissionDate.isAfter(oneWeekAgo)) {
                repairOutputDTOList.add(transferRepairToDTO(repair));
            }
        }

        return repairOutputDTOList;
    }




    public Repair transferInputDtoRepairToRepair(RepairInputDTO repairInputDTO){
        Repair repair = new Repair();

        repair.setRepairNumber(repairInputDTO.getRepairNumber());
        repair.setProblemDescription(repairInputDTO.getProblemDescription());
        repair.setSubmissionDate(repairInputDTO.getSubmissionDate());
        if (repairInputDTO.getUserId() != null) {
            User user = userRepository.findById(repairInputDTO.getUserId()).orElse(null);
            repair.setUser(user);
        }
        if (repairInputDTO.getProductId() != null) {
            Product product = productRepository.findById(repairInputDTO.getProductId()).orElse(null);
            repair.setProduct(product);
        }


        return repair;
    }

    public RepairOutputDTO transferRepairToDTO(Repair repair){
        RepairOutputDTO repairOutputDTO = new RepairOutputDTO();

        repairOutputDTO.setRepairNumber(repair.getRepairNumber());
        repairOutputDTO.setProblemDescription(repair.getProblemDescription());
        if (repair.getUser() != null) {
            repairOutputDTO.setUserId(repair.getUser().getId());
        }

        if (repair.getProduct() != null) {
            repairOutputDTO.setProductId(repair.getProduct().getId());
        }
        repairOutputDTO.setSubmissionDate(repair.getSubmissionDate());

        return repairOutputDTO;
    }

}
