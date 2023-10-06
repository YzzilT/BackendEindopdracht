package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.OrderLineOutputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Order;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.model.ProductType;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import com.example.backendeindopdracht.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderLineService orderLineService;
    private final OrderLineRepository orderLineRepository;


    //POST
//    public ProductOutputDTO createProduct (ProductInputDTO productInputDTO){
//
//        Product product = transferInputDtoProductToProduct(productInputDTO);
//
//        productRepository.save(product);
//        Long productId = product.getId();
//
//        OrderLineOutputDTO orderLineOutputDTO = orderLineService.addProductToOrderLine(productId, 1);
//
//        product.getOrderLines().add(orderLineOutputDTO);
//        return transferProductToDTO(product);
//
//    }

    public ProductOutputDTO createProduct (ProductInputDTO productInputDTO) {

        Product product = transferInputDtoProductToProduct(productInputDTO);
        productRepository.save(product);

//        OrderLine orderLine;
//
//
//        Optional<OrderLine> existingOrderLine = orderLineRepository.findByProduct(product);
//
//        if (existingOrderLine.isPresent()) {
//            orderLine = existingOrderLine.get();
//            int newQuantity = orderLine.getQuantity() + +1;
//            orderLine.setQuantity(newQuantity);
//            BigDecimal unitPrice = BigDecimal.valueOf(orderLine.getProduct().getPrice());
//            BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(newQuantity));
//            orderLine.setTotalAmount(totalAmount);
//            orderLineRepository.save(orderLine);
//
//        } else {
//
//            orderLine = new OrderLine();
//            orderLine.setProduct(product);
//            orderLine.setQuantity(1);
//            BigDecimal unitPrice = BigDecimal.valueOf(product.getPrice());
//            BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(1));
//            orderLine.setTotalAmount(totalAmount);
//            orderLineRepository.save(orderLine);
//
//        }
//        product.getOrderLines().add(orderLine);
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
        product.setOriginalStock(productInputDTO.getOriginalStock());
        product.setCurrentStock(productInputDTO.getCurrentStock());
        product.setDescription(productInputDTO.getDescription());
        product.setProductType(productInputDTO.getProductType());
        // TODO: 10/4/2023 how to add order and list orderlines?


        return product;

    }

    public ProductOutputDTO transferProductToDTO(Product product){
        ProductOutputDTO productOutputDTO = new ProductOutputDTO();

        productOutputDTO.setId(product.getId());
        productOutputDTO.setName(product.getName());
        productOutputDTO.setPrice(product.getPrice());
        productOutputDTO.setOriginalStock(product.getOriginalStock());
        productOutputDTO.setCurrentStock(product.getCurrentStock());
        productOutputDTO.setDescription(product.getDescription());
        productOutputDTO.setProductType(product.getProductType());
        // TODO: 10/4/2023 how to add order list orderlines?

        return productOutputDTO;
    }














}
