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
    public InvoiceController(final InvoiceService invoiceService,final SessionManager sessionManager) {
        this.invoiceService = invoiceService;
        this.sessionManager = sessionManager;
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

    /* token para tener la sesion que contiene adentro el objeto Client
      @RequestBody DateCRequestDto datesDto, el token
      Consulta de facturas del usuario logueado por rango de fechas.

       */
  /*  @GetMapping("")
    public ResponseEntity<List<Invoice>> getMessages(@RequestHeader("Authorization") String sessionToken) {
        Client currentUser = sessionManager.getCurrentUser(sessionToken);
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Message> messages = messageController.getMessages(currentUser.getUserId());
        return (messages.size() > 0) ? ResponseEntity.ok(messages) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
