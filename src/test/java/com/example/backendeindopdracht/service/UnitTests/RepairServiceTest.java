package com.example.backendeindopdracht.service.UnitTests;

import com.example.backendeindopdracht.repository.RepairRepository;
import com.example.backendeindopdracht.service.RepairService;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import com.example.backendeindopdracht.DTO.outputDTO.RepairOutputDTO;
import com.example.backendeindopdracht.model.Repair;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


class RepairServiceTest {

    @InjectMocks
    private RepairService repairService;

    @Mock
    private RepairRepository repairRepository;

    @Test
    void shouldRetrieveRepairsSubmittedOverAWeekAgo() {
        LocalDate now = LocalDate.now();
        LocalDate oneWeekAgo = now.minusWeeks(1);

        List<Repair> mockRepairs = new ArrayList<>();
        mockRepairs.add(createRepairWithSubmissionDate(oneWeekAgo.minusDays(1)));
        mockRepairs.add(createRepairWithSubmissionDate(oneWeekAgo.plusDays(1)));
        mockRepairs.add(createRepairWithSubmissionDate(oneWeekAgo.plusDays(7)));
        mockRepairs.add(createRepairWithSubmissionDate(oneWeekAgo.plusDays(8)));


        when(repairRepository.findAll()).thenReturn(mockRepairs);


        List<RepairOutputDTO> result = repairService.getRepairsSubmittedOverAWeekAgo();


        assertEquals(3, result.size());


    }

    private Repair createRepairWithSubmissionDate(LocalDate submissionDate) {
        Repair repair = new Repair();
        repair.setSubmissionDate(submissionDate);

        return repair;
    }

}
