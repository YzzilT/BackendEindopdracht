package com.example.backendeindopdracht.repository;

import com.example.backendeindopdracht.model.OrderLine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface OrderLineRepository extends CrudRepository<OrderLine, Long> {
    List<OrderLine> findByProduct_Id(Long productId);

    List<OrderLine> findByOrder_Id(Long productId);
}
