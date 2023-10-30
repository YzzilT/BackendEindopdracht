package com.example.backendeindopdracht.controller;


import com.example.backendeindopdracht.DTO.inputDTO.InvoiceInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.InvoiceOutputDTO;
import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;


    //POST
    @PostMapping()
    public ResponseEntity<InvoiceOutputDTO> addInvoice (@RequestBody InvoiceInputDTO invoiceInputDTO){
        InvoiceOutputDTO addedInvoice = invoiceService.addInvoice(invoiceInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedInvoice);
    }

    //GET ALL
    @GetMapping
    public ResponseEntity<List<InvoiceOutputDTO>> getAllInvoices(){
        return ResponseEntity.ok().body(invoiceService.getAllInvoices());
    }

    //GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceOutputDTO> getInvoiceById(@PathVariable Long id){
        return ResponseEntity.ok().body(invoiceService.getInvoiceById(id));
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceOutputDTO> updateInvoice (@PathVariable Long id, @Valid @RequestBody InvoiceInputDTO invoiceInputDTO){
        return ResponseEntity.ok().body(invoiceService.updateInvoice(invoiceInputDTO, id));
    }


    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Invoice> deleteInvoice (@PathVariable Long id){
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    //calculateTotalAmountWithVAT
    @GetMapping("/totalAmount")
    public ResponseEntity<BigDecimal> calculateTotalAmountWithVAT(@RequestBody InvoiceInputDTO invoiceInputDTO){
        BigDecimal totalAmountIncludingVAT = invoiceService.calculateTotalAmountWithVAT(invoiceInputDTO);
        return ResponseEntity.ok(totalAmountIncludingVAT);
    }
}
