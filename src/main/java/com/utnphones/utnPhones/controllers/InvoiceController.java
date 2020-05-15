package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/")
    public List<Invoice> getAll(){
        return this.invoiceService.getAll();
    }

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice){
        return this.invoiceService.create(invoice);
    }

    @GetMapping("/{idInvoice}")
    public Invoice getById(@PathVariable Integer id){
        return this.invoiceService.getById(id).get();
    }
}
