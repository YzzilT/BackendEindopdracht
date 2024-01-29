package com.example.backendeindopdracht.service.UnitTests;


import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.model.ProductType;
import com.example.backendeindopdracht.repository.ProductRepository;
import com.example.backendeindopdracht.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
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
    void testCreateProduct() {
        // Mock data
        ProductInputDTO inputDTO = new ProductInputDTO();

        inputDTO.setName("TestProduct");
        inputDTO.setPrice(19.99);
        inputDTO.setOriginalStock(50);
        inputDTO.setCurrentStock(50);
        inputDTO.setDescription("Description");
        inputDTO.setProductType(ProductType.GLOVES);
        inputDTO.setOrderid(1);

        Product product = new Product();
        product.setId(1L);
        product.setName("TestProduct");
        product.setPrice(19.99);
        product.setOriginalStock(50);
        product.setCurrentStock(50);
        product.setDescription("Description");
        product.setProductType(ProductType.GLOVES);


        ProductOutputDTO expectedOutputDTO = new ProductOutputDTO();
        expectedOutputDTO.setId(1L);
        expectedOutputDTO.setName("TestProduct");
        expectedOutputDTO.setPrice(19.99);
        expectedOutputDTO.setOriginalStock(50);
        expectedOutputDTO.setCurrentStock(50);
        expectedOutputDTO.setDescription("Description");
        expectedOutputDTO.setProductType(ProductType.GLOVES);

        // Mock behavior
        when(productRepository.save(any())).thenReturn(product);

        // Test
        ProductOutputDTO result = productService.createProduct(inputDTO);

        // Verify
        assertNotNull(result);
        assertEquals(expectedOutputDTO.getId(), result.getId());
        assertEquals(expectedOutputDTO.getName(), result.getName());
        assertEquals(expectedOutputDTO.getPrice(), result.getPrice());
        assertEquals(expectedOutputDTO.getOriginalStock(), result.getOriginalStock());
        assertEquals(expectedOutputDTO.getCurrentStock(), result.getCurrentStock());
        assertEquals(expectedOutputDTO.getDescription(), result.getDescription());
        assertEquals(expectedOutputDTO.getProductType(), result.getProductType());

        // Additional assertions based on your actual implementation

        verify(productRepository, times(1)).save(any());
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