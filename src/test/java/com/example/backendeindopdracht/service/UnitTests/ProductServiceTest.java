package com.example.backendeindopdracht.service.UnitTests;


import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testUpdateStockWhenBuyingProduct() {

        Product sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setCurrentStock(10);


        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        try {
            productService.updateStockWhenBuyingProduct(1L, 5);
        } catch (Exception e) {
            fail("Exception should not be thrown when buying 5 units.");
        }


        assertEquals(5, sampleProduct.getCurrentStock());


        try {
            productService.updateStockWhenBuyingProduct(1L, 10);
            fail("Exception should be thrown when buying more than available stock.");
        } catch (Exception e) {
            assertEquals("Not enough in stock", e.getMessage());
        }


        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        try {
            productService.updateStockWhenBuyingProduct(2L, 5);
            fail("Exception should be thrown when buying a non-existing product.");
        } catch (Exception e) {
            assertEquals("Product not found", e.getMessage());
        }
    }


}