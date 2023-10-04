package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.InvoiceInputDTO;
import com.example.backendeindopdracht.DTO.inputDTO.RoleInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.InvoiceOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.model.Role;
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
        InvoiceOutputDTO invoiceOutputDTO = transferInvoiceToOutputDTO(invoice);

        return invoiceOutputDTO;
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
            throw new RecordNotFoundException("No invoice found with id: " + id);
        }
        Invoice invoice = optionalInvoice.get();
        return transferInvoiceToOutputDTO(invoice);
    }

    //PUT
    public InvoiceOutputDTO updateInvoice (InvoiceInputDTO invoiceInputDTO, Long id){
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()){
            throw new RecordNotFoundException("No invoice found with id " + id);
        } else {
            Invoice updateInvoice = transferInvoiceInputDtoToInvoice(invoiceInputDTO);
            updateInvoice.setInvoiceNumber(id);
            Invoice updatedInvoice = invoiceRepository.save(updateInvoice);

            return transferInvoiceToOutputDTO(updatedInvoice);
        }
    }


    //DELETE
    public Invoice deleteInvoice(Long id){
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()){
            throw new RecordNotFoundException("No invoice found with id: " + id);
        }
        invoiceRepository.deleteById(id);
        return optionalInvoice.get();
    }





    public Invoice transferInvoiceInputDtoToInvoice(InvoiceInputDTO invoiceInputDTO){

        Invoice invoice = new Invoice();

        invoice.setCustomerName(invoiceInputDTO.getCustomerName());
        invoice.setInvoiceDate(invoiceInputDTO.getInvoiceDate());
        invoice.setTotalAmount(invoiceInputDTO.getTotalAmount());

        return invoice;
    }


    public InvoiceOutputDTO transferInvoiceToOutputDTO (Invoice invoice){

        InvoiceOutputDTO invoiceOutputDTO = new InvoiceOutputDTO();

        invoice.setCustomerName(invoice.getCustomerName());
        invoice.setInvoiceDate(invoice.getInvoiceDate());
        invoice.setTotalAmount(invoice.getTotalAmount());

        return invoiceOutputDTO;
    }
}
