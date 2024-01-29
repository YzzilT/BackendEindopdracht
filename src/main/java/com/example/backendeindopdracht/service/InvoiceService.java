package com.example.backendeindopdracht.service;


import com.example.backendeindopdracht.DTO.inputDTO.InvoiceInputDTO;
import com.example.backendeindopdracht.DTO.outputDTO.InvoiceOutputDTO;
import com.example.backendeindopdracht.model.Invoice;
import com.example.backendeindopdracht.model.OrderLine;
import com.example.backendeindopdracht.repository.InvoiceRepository;
import com.example.backendeindopdracht.repository.OrderLineRepository;
import com.example.backendeindopdracht.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderRepository orderRepository;

    private final OrderLineRepository orderLineRepository;



    public InvoiceOutputDTO addInvoice (InvoiceInputDTO invoiceInputDTO){

        Invoice invoice = transferInvoiceInputDtoToInvoice(invoiceInputDTO);
        invoice.setOrder(orderRepository.findById(invoiceInputDTO.getOrderId()).orElse(null));
        invoiceRepository.save(invoice);
        InvoiceOutputDTO invoiceOutputDTO = transferInvoiceToOutputDTO(invoice);

        return invoiceOutputDTO;
    }


    public List<InvoiceOutputDTO> getAllInvoices(){
        Iterable<Invoice> invoices = invoiceRepository.findAll();
        List<InvoiceOutputDTO> invoiceOutputDTOList = new ArrayList<>();

        for (Invoice invoice : invoices){
            invoiceOutputDTOList.add(transferInvoiceToOutputDTO(invoice));
        }
        return invoiceOutputDTOList;
    }


    public  InvoiceOutputDTO getInvoiceById(Long id){
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);

        Invoice invoice = optionalInvoice.orElseThrow();
        return transferInvoiceToOutputDTO(invoice);
    }


    public InvoiceOutputDTO updateInvoice (InvoiceInputDTO invoiceInputDTO, Long id){
        Invoice updateInvoice = transferInvoiceInputDtoToInvoice(invoiceInputDTO);
        updateInvoice.setInvoiceNumber(id);
        var order = orderRepository.findById(invoiceInputDTO.getOrderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"order not found"));
        updateInvoice.setOrder(order);
        Invoice updatedInvoice = invoiceRepository.save(updateInvoice);

        return transferInvoiceToOutputDTO(updatedInvoice);
    }



    public Invoice deleteInvoice(Long id){
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);

        invoiceRepository.deleteById(id);
        return optionalInvoice.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"invoice not found"));
    }

    public BigDecimal calculateTotalAmount(long orderid){

        List<OrderLine> orderlines = orderLineRepository.findByOrder_Id(orderid);

        BigDecimal sum = new BigDecimal(0);
        for (OrderLine orderline : orderlines) {
            sum = sum.add(orderline.getTotalAmount());
        }

        return sum;
    }


    public Invoice transferInvoiceInputDtoToInvoice(InvoiceInputDTO invoiceInputDTO){

        Invoice invoice = new Invoice();


        invoice.setInvoiceNumber(invoiceInputDTO.getInvoiceNumber());
        invoice.setCustomerName(invoiceInputDTO.getCustomerName());
        invoice.setInvoiceDate(invoiceInputDTO.getInvoiceDate());
        invoice.setTotalAmount(invoiceInputDTO.getTotalAmount());
        invoice.setEmail(invoiceInputDTO.getEmail());
        invoice.setOrder(orderRepository.findById(invoiceInputDTO.getOrderId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"order not found")));

        return invoice;
    }


    public InvoiceOutputDTO transferInvoiceToOutputDTO (Invoice invoice){

        InvoiceOutputDTO invoiceOutputDTO = new InvoiceOutputDTO();

        invoiceOutputDTO.setInvoiceNumber(invoice.getInvoiceNumber());
        invoiceOutputDTO.setCustomerName(invoice.getCustomerName());
        invoiceOutputDTO.setInvoiceDate(invoice.getInvoiceDate());
        invoiceOutputDTO.setTotalAmount(invoice.getTotalAmount());
        invoiceOutputDTO.setEmail(invoice.getEmail());

        return invoiceOutputDTO;
    }
}
