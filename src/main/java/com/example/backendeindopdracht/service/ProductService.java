package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.ProductInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.ProductOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor

@Service
public class ProductService {

    private final ProductRepository productRepository;



    public ProductOutputDTO createProduct(ProductInputDTO productInputDTO) {

        Product product = transferInputDtoProductToProduct(productInputDTO);
        productRepository.save(product);

        return transferProductToDTO(product);

    }



    public List<ProductOutputDTO> getAllProducts() {
        Iterable<Product> products = productRepository.findAll();
        List<ProductOutputDTO> productOutputDTOList = new ArrayList<>();

        for (Product product : products) {
            productOutputDTOList.add(transferProductToDTO(product));
        }
        return productOutputDTOList;
    }



    public ProductOutputDTO getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        Product product = optionalProduct.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"product not found"));
        return transferProductToDTO(product);
    }



    public ProductOutputDTO updateProduct(ProductInputDTO productInputDTO, Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RecordNotFoundException("No product found with id: " + id);
        } else {
            Product updateProduct = transferInputDtoProductToProduct(productInputDTO);
            updateProduct.setId(id);
            Product updatedProduct = productRepository.save(updateProduct);

            return transferProductToDTO(updatedProduct);
        }

    }



    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new RecordNotFoundException("No product found with id: " + id);
        }
        productRepository.deleteById(id);
    }




    public void updateStockWhenBuyingProduct(long productid,int amount) throws Exception {

        var product = productRepository.findById(productid).orElse(null);
        if(product == null){
            throw new Exception("Product not found");
        }
        if(product.getCurrentStock() < amount){
            throw new Exception("Not enough in stock");
        }

        product.setCurrentStock(product.getCurrentStock() - amount);
    }


    public List <ProductOutputDTO> findProductsThatNeedRestocking(){

        var allProducts = getAllProducts();
        var restockProducts = new ArrayList<ProductOutputDTO>();


        for (var product : allProducts){
            if (product.getCurrentStock() < product.getOriginalStock()){
                restockProducts.add(product);
            }
        }
        return restockProducts;
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


        return productOutputDTO;
    }

}
