package com.example.backendeindopdracht.repository;

import com.example.backendeindopdracht.model.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
