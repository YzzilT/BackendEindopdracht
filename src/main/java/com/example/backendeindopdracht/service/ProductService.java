package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    //POST
    public ProductOutputDTO createProduct (ProductInputDTO productInputDTO){

        Product product = transferInputDtoProductToProduct(productInputDTO);

        productRepository.save(product);

        ProductOutputDTO productOutputDTO = transferProductToDTO(product);

        return productOutputDTO;
    }
    /*public ProductOutputDTO addProduct (ProductInputDTO productInputDTO, Long orderId){
        Product product = transferInputDtoProductToProduct(productInputDTO);
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RecordNotFoundException("no order found with id: " + orderId));
        product.setOrder(order);
        order.getProducts().add(product);

        orderRepository.save(order); //save both the order and the product together

        return transferProductToDTO(product);
    }*/

    //GET ALL
    public List<ProductOutputDTO> getAllProducts(){
        Iterable<Product> products = productRepository.findAll();
        List<ProductOutputDTO> productOutputDTOList = new ArrayList<>();

        for (Product product : products){
            productOutputDTOList.add(transferProductToDTO(product));
        }
        return productOutputDTOList;
    }

    //GET BY ID
    public ProductOutputDTO getProductById( Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new RecordNotFoundException("No product found with id: " + id);
        }
        Product product = optionalProduct.get();
        return transferProductToDTO(product);
    }




   //PUT
    public ProductOutputDTO updateProduct(ProductInputDTO productInputDTO, Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new RecordNotFoundException("No product found with id: " + id);
        } else {
            Product updateProduct = transferInputDtoProductToProduct(productInputDTO);
            updateProduct.setId(id);
            Product updatedProduct = productRepository.save(updateProduct);

            return transferProductToDTO(updatedProduct);
        }

    }


    //DELETE
    public void deleteProduct(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            throw new RecordNotFoundException("No product found with id: " + id);
        }
        productRepository.deleteById(id);
    }



    public Product transferInputDtoProductToProduct(ProductInputDTO productInputDTO){
        Product product = new Product();

        product.setId(productInputDTO.getId());
        product.setName(productInputDTO.getName());
        product.setPrice(productInputDTO.getPrice());
        product.setCurrentStock(productInputDTO.getCurrentStock());
        product.setOriginalStock(productInputDTO.getOriginalStock());
        product.setDescription(productInputDTO.getDescription());
        product.setProductType(productInputDTO.getProductType());


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
        productOutputDTO.setProductType(product.getProductType());

        return productOutputDTO;
    }














}
