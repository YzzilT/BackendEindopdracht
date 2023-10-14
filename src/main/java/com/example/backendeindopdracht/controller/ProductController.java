package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.service.OrderLineService;
import com.example.backendeindopdracht.service.ProductService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final OrderLineService orderLineService;

    //POST
    @PostMapping("/add")
    public ResponseEntity<ProductOutputDTO> createProduct(@RequestBody @Valid ProductInputDTO productInputDto){
        ProductOutputDTO addedProduct = productService.createProduct(productInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    public ResponseEntity<String> koopProduct(long productid, long orderid) throws Exception {
//        var orderline = new OrderlineInputDTO();
//        orderline.setProductId(productid);
//        orderline.setOrderId(orderid);
//        orderLineService.addOrderLine(orderline);
//        var product = productService.getProductById(productid);
//        if(product.getCurrentStock() <= 0){
//            throw new Exception("product is out of stock");
//        }
//        product.setCurrentStock(product.getCurrentStock() - 1);
//        productService.updateProduct(product);
//
//        return null;
//    }

    public ResponseEntity<List<ProductOutputDTO>> ScanForProductsThatNeedRestocking(){
        var allproducts = productService.getAllProducts();
        var res = new ArrayList<ProductOutputDTO>();
        for (var product : allproducts) {
            if(product.getCurrentStock() < product.getOriginalStock()){
                res.add(product);
            }
        }
        return ResponseEntity.ok().body(res);
    }

    public ResponseEntity<String> mergeOrderlines(){
        return null;
    }

    public ResponseEntity<String> calculateTotalAmount(long btw){
        return null;
    }



    //GET ALL
    @GetMapping
    public ResponseEntity<List<ProductOutputDTO>> getAllProducts(){
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




