package com.example.backendeindopdracht.service.IntegrationTests;

import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceIntegrationTest {


        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ProductRepository productRepository;

        @Test
        @WithMockUser(username = "Lizzy", roles = "frontdesk")

        void createProduct() throws Exception {

            ProductInputDTO productInputDTO = new ProductInputDTO();
            productInputDTO.setName("Test Product");
            productInputDTO.setPrice(10.0);


            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/products")
                            .contentType("application/json")
                            .content(asJsonString(productInputDTO)))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andReturn();


            String responseBody = result.getResponse().getContentAsString();
            ProductOutputDTO productOutputDTO = new ObjectMapper().readValue(responseBody, ProductOutputDTO.class);

            assertThat(productOutputDTO).isNotNull();
            assertThat(productOutputDTO.getId()).isNotNull();
            assertThat(productOutputDTO.getName()).isEqualTo(productInputDTO.getName());
            assertThat(productOutputDTO.getPrice()).isEqualTo(productInputDTO.getPrice());


            var createdProduct = productRepository.findById(productOutputDTO.getId()).orElse(null);
            assertThat(createdProduct).isNotNull();
            assertThat(createdProduct.getName()).isEqualTo(productInputDTO.getName());
            assertThat(createdProduct.getPrice()).isEqualTo(productInputDTO.getPrice());
        }

        private String asJsonString(final Object obj) {
            try {
                return new ObjectMapper().writeValueAsString(obj);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }





