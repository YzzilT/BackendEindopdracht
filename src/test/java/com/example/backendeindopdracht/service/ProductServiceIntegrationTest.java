package com.example.backendeindopdracht.service;

import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ProductServiceIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createProduct() {
        ProductInputDTO productInputDTO = new ProductInputDTO();
        productInputDTO.setName("Test Product");
        productInputDTO.setPrice(10.0);

        try {
            mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                            .contentType("application/json")
                            .content(asJsonString(productInputDTO)))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        } catch (Exception e) {
            fail("Error occurred during the createProduct test: " + e.getMessage());
        }
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
