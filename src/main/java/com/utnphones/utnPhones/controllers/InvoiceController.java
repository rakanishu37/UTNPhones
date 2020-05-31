package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.services.InvoiceService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private InvoiceService invoiceService;
    private SessionManager sessionManager;

    @Autowired
    public InvoiceController(final InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
//TODO verificar que sea un empleado quien accede a las url
    @GetMapping("/")
    public List<Invoice> getAll(){
        return this.invoiceService.getAll();
    }

    @PostMapping //todo illegalrequestexception
    public Invoice create(@RequestBody Invoice invoice){
        return this.invoiceService.create(invoice);
    }

    @GetMapping("/{idInvoice}")
    public Invoice getById(@PathVariable Integer id) throws Exception {
        return this.invoiceService.getById(id);
    }
}
