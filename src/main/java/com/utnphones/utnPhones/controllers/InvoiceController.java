package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.exceptions.InvoiceNotFoundException;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.List;

@Controller
public class InvoiceController {
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    public List<InvoiceByClient> getInvoicesByClient(Integer idClient, String dateFrom, String dateTo) throws ParseException {
        return this.invoiceService.getInvoicesByClient(idClient, dateFrom, dateTo);
    }

}
