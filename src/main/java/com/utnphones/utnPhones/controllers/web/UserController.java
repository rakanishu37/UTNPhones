package com.utnphones.utnPhones.controllers.web;


import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.MissingTokenException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoicesDates;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    private ClientController clientController;
    private CallController callController;
    private InvoiceController invoiceController;
    private SessionManager sessionManager;

    @Autowired
    public UserController(ClientController clientController,CallController callController,SessionManager sessionManager,InvoiceController invoiceController) {
        this.clientController = clientController;
        this.invoiceController= invoiceController;
        this.callController = callController;
        this.sessionManager= sessionManager;
    }

    @GetMapping("/me/calls")
    public ResponseEntity<Map<String, List<CallsDates>>> getCallsBetweenDates(@RequestHeader("Authorization") String token,@RequestParam(name = "dateFrom") String dateFrom, @RequestParam(name = "dateTo") String dateTo) throws ParseException, UserNotLoggedException, MissingTokenException {
        Date from = new SimpleDateFormat("yyyy/MM/dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse(dateTo);
        Integer idClient = sessionManager.getCurrentUser(token).getId();
        Map<String, List<CallsDates>> callsBetweenDates = callController.getCallsBetweenDates(idClient, from, to);

        return (!callsBetweenDates.isEmpty()) ? ResponseEntity.ok(callsBetweenDates) :
                ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/me/invoices")
    public ResponseEntity<List<InvoicesDates>> getInvoicesBetweenDates(@RequestHeader("Authorization") String token,
                                                                        @RequestParam(name = "dateFrom") String dateFrom,
                                                                        @RequestParam(name = "dateTo") String dateTo) throws ParseException, UserNotLoggedException, MissingTokenException {

        Date from = new SimpleDateFormat("yyyy/MM/dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse(dateTo);
        Integer idClient = sessionManager.getCurrentUser(token).getId();
        List<InvoicesDates> invoices = invoiceController.getInvoicesBetweenDates(idClient, from, to);
        return (!invoices.isEmpty()) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
