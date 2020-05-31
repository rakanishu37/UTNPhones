package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.projections.InvoicesDates;
import com.utnphones.utnPhones.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAll(){
        List<Client> list = clientService.getAll();
        return (list.size() > 0) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<Client> getById(@PathVariable Integer idClient) throws ClientNotFoundException {
        return ResponseEntity.ok(clientService.getById(idClient));
    }

    @PostMapping("/")
    public ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(client));
    }

    @PostMapping("/{idClient}/phonelines")
    public ResponseEntity<PhoneLine> createPhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(clientService.getById(idClient));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.setPhoneline(phoneLine));
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer idClient, @RequestBody Client updatedClient) throws ClientNotFoundException {
        return ResponseEntity.ok(clientService.update(idClient,updatedClient));
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer idClient) throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        return ResponseEntity.ok(clientService.delete(idClient));
    }

    @GetMapping("/{idClient}/calls")
    public ResponseEntity<Map<String, List<CallsDates>>> getCallsBetweenDates(@PathVariable Integer idClient,
                                                                              @RequestParam(name = "dateFrom") String dateFrom,
                                                                              @RequestParam(name = "dateTo") String dateTo) throws ClientNotFoundException, ParseException, ValidationException {
        Client client = this.clientService.getById(idClient);
        Date from = new SimpleDateFormat("yyyy/MM/dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse(dateTo);
        Map<String, List<CallsDates>> callsBetweenDates = clientService.getCallsBetweenDates(client.getId(), from, to);

        return (!callsBetweenDates.isEmpty()) ? ResponseEntity.ok(callsBetweenDates) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{idClient}/invoices")
    public ResponseEntity<List<InvoicesDates>> getInvoicesBetweenDates(@PathVariable Integer idClient,
                                                                       @RequestParam(name = "dateFrom") String dateFrom,
                                                                       @RequestParam(name = "dateTo") String dateTo) throws ClientNotFoundException, ParseException, ValidationException {

        Date from = new SimpleDateFormat("yyyy/MM/dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse(dateTo);
        List<InvoicesDates> invoices = clientService.getInvoicesBetweenDates(idClient, from, to);
        return (!invoices.isEmpty()) ? ResponseEntity.ok(invoices) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
