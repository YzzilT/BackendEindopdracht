package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDTO.OrderlineInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.service.OrderLineService;
import com.example.backendeindopdracht.service.ProductService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;



    @PostMapping
    public ResponseEntity<ProductOutputDTO> createProduct(@RequestBody @Valid ProductInputDTO productInputDto){
        ProductOutputDTO addedProduct = productService.createProduct(productInputDto);

        return ResponseEntity.status(HttpStatus.CREATED).header("Location", "/products/{id}" + addedProduct.getId()).body(addedProduct);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    public ResponseEntity<String> updateStockWhenBuyingProduct (long productid, int amount) throws Exception {
        productService.updateStockWhenBuyingProduct(productid,amount);
        return ResponseEntity.ok("");
    }

    @GetMapping("/restock")
    public ResponseEntity<List<ProductOutputDTO>> findProductsThatNeedRestocking(){
        List<ProductOutputDTO> restockProducts = productService.findProductsThatNeedRestocking();
        return ResponseEntity.ok().body(restockProducts);
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




