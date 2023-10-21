package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.InvoiceInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.InvoiceOutputDTO;
import com.example.backendeindopdracht.exceptions.RecordNotFoundException;
import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.model.Product;
import com.example.backendeindopdracht.repository.InvoiceRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;


    //POST
    public InvoiceOutputDTO addInvoice (InvoiceInputDTO invoiceInputDTO){

        Invoice invoice = transferInvoiceInputDtoToInvoice(invoiceInputDTO);
        invoice.setOrder(orderRepository.findById(invoiceInputDTO.getOrderId()).orElse(null));
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
            var order = orderRepository.findById(invoiceInputDTO.getOrderId()).get();
            updateInvoice.setOrder(order);
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

    public BigDecimal calculateTotalAmountWithVAT(InvoiceInputDTO invoiceInputDTO){
        BigDecimal VAT = new BigDecimal("0.21");
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (Product product : invoiceInputDTO.getProducts()){
            BigDecimal productPrice = BigDecimal.valueOf(product.getPrice());
            totalAmount = totalAmount.add(productPrice);
        }

        BigDecimal vatAmount = totalAmount.multiply(VAT);
        BigDecimal totalAmountIncludingVAT = totalAmount.add(vatAmount);

        return totalAmountIncludingVAT.setScale(2, RoundingMode.HALF_UP);
    }


    public Invoice transferInvoiceInputDtoToInvoice(InvoiceInputDTO invoiceInputDTO){

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(invoiceInputDTO.getInvoiceNumber());
        invoice.setCustomerName(invoiceInputDTO.getCustomerName());
        invoice.setInvoiceDate(invoiceInputDTO.getInvoiceDate());
        invoice.setTotalAmount(invoiceInputDTO.getTotalAmount());
        invoice.setEmail(invoiceInputDTO.getEmail());
        invoice.setOrderId(invoiceInputDTO.getOrderId());
        invoice.setOrder(invoiceInputDTO.getOrder());

        return invoice;
    }


    public InvoiceOutputDTO transferInvoiceToOutputDTO (Invoice invoice){

        InvoiceOutputDTO invoiceOutputDTO = new InvoiceOutputDTO();

        invoiceOutputDTO.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceOutputDTO.setCustomerName(invoice.getCustomerName());
        invoiceOutputDTO.setInvoiceDate(invoice.getInvoiceDate());
        invoiceOutputDTO.setTotalAmount(invoice.getTotalAmount());
        invoiceOutputDTO.setEmail(invoice.getEmail());
        invoiceOutputDTO.setOrderId(invoice.getOrderId());
        invoiceOutputDTO.setOrder(invoice.getOrder());


        return invoiceOutputDTO;
    }
}
