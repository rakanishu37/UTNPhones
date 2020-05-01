package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;

public class InvoiceController {
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
}
