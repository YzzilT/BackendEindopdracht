package com.example.backendeindopdracht.repository;

import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {
    Optional<OrderLine> findByProduct(Product product);
}
