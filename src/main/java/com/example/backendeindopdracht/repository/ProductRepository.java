package com.example.backendeindopdracht.repository;


import com.example.backendeindopdracht.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Optional<Product> findById(Long id);  //IS THIS NECESSARY?
}
