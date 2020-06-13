package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.projections.InvoicesDates;
import com.utnphones.utnPhones.services.InvoiceService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
public class InvoiceController {
    private InvoiceService invoiceService;
    private SessionManager sessionManager;

    @Autowired
    public InvoiceController(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    public List<Invoice> getAll(){
        return this.invoiceService.getAll();
    }

    public Invoice create(@RequestBody Invoice invoice){
        return this.invoiceService.create(invoice);
    }

    public Invoice getById(@PathVariable Integer id) throws Exception {
        return this.invoiceService.getById(id);
    }

    public List<InvoiceByClient> getInvoicesByClient(Integer idClient, String dateFrom, String dateTo) throws ParseException {
        return this.invoiceService.getInvoicesByClient(idClient, dateFrom, dateTo);
    }

}
