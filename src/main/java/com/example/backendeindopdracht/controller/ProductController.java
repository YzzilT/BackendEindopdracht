package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //POST
    @PostMapping("/add")
    public ResponseEntity<ProductOutputDTO> createProduct(@RequestBody ProductInputDTO productInputDto){
        ProductOutputDTO addedProduct = productService.createProduct(productInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    //GET ALL
    @GetMapping
    public ResponseEntity<List<ProductOutputDTO>> getAllproducts(){
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductOutputDTO> getProductById(@PathVariable Long id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

   //PUT
    @PutMapping("/{id}")
    public ResponseEntity<ProductOutputDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductInputDTO productInputDTO){
        return ResponseEntity.ok().body(productService.updateProduct(productInputDTO, id));
    }


    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct (@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


}




