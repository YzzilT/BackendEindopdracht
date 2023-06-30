package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDto.InvoiceInputDTO;
import com.example.backendeindopdracht.DTO.outputDto.InvoiceOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;


    //POST
    public InvoiceOutputDTO addInvoice (InvoiceInputDTO invoiceInputDTO){

        Invoice invoice = transferInvoiceInputDtoToInvoice(invoiceInputDTO);
        invoiceRepository.save(invoice);

        return transferInvoiceToOutputDTO(invoice);
    }

    //GET ALL
    public List<InvoiceOutputDTO> getAllInvoices(){
        Iterable<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceOutputDTO> invoiceOutputDTOList = new ArrayList<>();

        for (Invoice invoice : invoices){
            invoiceOutputDTOList.add(transferInvoiceToOutputDTO(invoice));
        }
        return invoiceOutputDTOList;
    }

    //GET BY ID
    public  InvoiceOutputDTO getInvoiceById(Long id){
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()){
            throw new RecordNotFoundException("No product found with id: " + id);
        }
        Invoice invoice = optionalInvoice.get();
        return transferInvoiceToOutputDTO(invoice);
    }

    //PUT


    //DELETE
    public void deleteInvoice(Long id){
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()){
            throw new RecordNotFoundException("No invoice found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }





    public Invoice transferInvoiceInputDtoToInvoice(InvoiceInputDTO invoiceInputDTO){

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(invoiceInputDTO.getInvoiceNumber());
        invoice.setCustomerName(invoiceInputDTO.getCustomerName());
        invoice.setInvoiceDate(invoiceInputDTO.getInvoiceDate());
        invoice.setTotalAmount(invoiceInputDTO.getTotalAmount());

        return invoice;
    }


    public InvoiceOutputDTO transferInvoiceToOutputDTO (Invoice invoice){

        InvoiceOutputDTO invoiceOutputDTO = new InvoiceOutputDTO();

        invoiceOutputDTO.setInvoiceNumber(invoice.getInvoiceNumber());
        invoice.setCustomerName(invoice.getCustomerName());
        invoice.setInvoiceDate(invoice.getInvoiceDate());
        invoice.setTotalAmount(invoice.getTotalAmount());

        return invoiceOutputDTO;
    }
}
