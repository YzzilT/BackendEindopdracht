package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDto.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.ProductOutputDTO;
import com.example.backendeindopdracht.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //POST
    @PostMapping("/add")
    public ResponseEntity<ProductOutputDTO> addProduct (@RequestBody ProductInputDTO productInputDto){
        ProductOutputDTO addedProduct = productService.addProduct(productInputDto);
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



}
