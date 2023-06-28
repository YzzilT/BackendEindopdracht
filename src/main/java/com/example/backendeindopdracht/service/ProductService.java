package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDto.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.ProductOutputDTO;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;


    //POST
    public ProductOutputDTO addProduct (ProductInputDTO productInputDTO){
        Product product = transferInputDTOProductToProduct(productInputDTO);
        productRepository.save(product);
        return transferProductToDTO(product);
    }

    //GET ALL
    public List<ProductOutputDTO> getAllProducts(){
        Iterable<Product> products = productRepository.findAll();
        List<ProductOutputDTO> productOutputDTOList = new ArrayList<>();

        for (Product product : products){
            productOutputDTOList.add(transferProductToDTO(product));
        }
        return productOutputDTOList;
    }


    public Product transferInputDTOProductToProduct(ProductInputDTO productInputDTO){
        Product product = new Product();

        product.setName(productInputDTO.getName());
        product.setPrice(productInputDTO.getPrice());
        product.setCurrentStock(productInputDTO.getCurrentStock());
        product.setOriginalStock(productInputDTO.getOriginalStock());
        product.setDescription(productInputDTO.getDescription());

        return product;

    }

    public ProductOutputDTO transferProductToDTO(Product product){
        ProductOutputDTO productOutputDTO = new ProductOutputDTO();

        productOutputDTO.setId(product.getId());
        productOutputDTO.setName(product.getName());
        productOutputDTO.setCurrentStock(product.getCurrentStock());
        productOutputDTO.setOriginalStock(product.getOriginalStock());
        productOutputDTO.setPrice(product.getPrice());
        productOutputDTO.setDescription(product.getDescription());

        return productOutputDTO;
    }














}
