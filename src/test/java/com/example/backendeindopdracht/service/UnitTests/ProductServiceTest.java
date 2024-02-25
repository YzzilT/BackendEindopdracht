package com.example.backendeindopdracht.service.UnitTests;


import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.model.ProductType;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test

        public void testCreateProduct() {

            Mockito.when(productRepository.save(any(Product.class))).thenReturn(createMockProduct());


            ProductInputDTO inputDTO = new ProductInputDTO();
            inputDTO.setName("Test Product");
            inputDTO.setPrice(10.0);
            inputDTO.setOriginalStock(50);
            inputDTO.setCurrentStock(50);
            inputDTO.setDescription("Test Description");
            inputDTO.setProductType(ProductType.GLOVES);


            ProductOutputDTO outputDTO = productService.createProduct(inputDTO);


            assertEquals("Test Product", outputDTO.getName());
            assertEquals(10.0, outputDTO.getPrice());
            assertEquals(50, outputDTO.getOriginalStock());
            assertEquals(50, outputDTO.getCurrentStock());
            assertEquals("Test Description", outputDTO.getDescription());
            assertEquals(ProductType.GLOVES, outputDTO.getProductType());
        }

        private Product createMockProduct() {

            Product product = new Product();
            product.setId(1L);
            product.setName("Test Product");
            product.setPrice(10.0);
            product.setOriginalStock(50);
            product.setCurrentStock(50);
            product.setDescription("Test Description");
            product.setProductType(ProductType.GLOVES);
            return product;
        }


    @Test
    void testUpdateStockWhenBuyingProduct() throws Exception {

        long productId = 1L;
        int amount = 5;
        Product product = new Product();
        product.setCurrentStock(10);


        when(productRepository.findById(productId)).thenReturn(Optional.of(product));


        assertDoesNotThrow(() -> productService.updateStockWhenBuyingProduct(productId, amount));


        assertEquals(5, product.getCurrentStock());

    }


}