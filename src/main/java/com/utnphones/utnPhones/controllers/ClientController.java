package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.PhoneLineService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private ClientService clientService;
    private PhoneLineService phoneLineService;
    private CallService callService;
    @Autowired
    public ClientController(ClientService clientService, PhoneLineService phoneLineService, CallService callService) {
        this.clientService = clientService;
        this.phoneLineService = phoneLineService;
        this.callService = callService;
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
    public ResponseEntity<Client> create(@RequestBody Client client){
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(client));
    }


    @PostMapping("/{idClient}/phonelines")
    public ResponseEntity<PhoneLine> createPhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(clientService.getById(idClient));
        return ResponseEntity.status(HttpStatus.CREATED).body(phoneLineService.create(phoneLine));
    }

    @PutMapping("/{idClient}/phonelines/")
    public ResponseEntity<PhoneLine> updatePhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(clientService.getById(idClient));
        return ResponseEntity.ok(phoneLineService.updatePhoneLine(phoneLine));
    }


    @DeleteMapping("/{idClient}/phonelines")
    public ResponseEntity<Integer> deletePhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(this.clientService.getById(idClient));
        return ResponseEntity.ok(phoneLineService.deletePhoneLine(phoneLine));
    }

    @PutMapping("/{idClient}")
    public ResponseEntity<Client> updateClient(@PathVariable Integer idClient, @RequestBody Client updatedClient) throws ClientNotFoundException {
        this.clientService.getById(idClient);
        return ResponseEntity.ok(clientService.update(updatedClient));
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity<Integer> deleteClient(@PathVariable Integer idClient) throws ClientNotFoundException {
        Client client = this.clientService.getById(idClient);
        return ResponseEntity.ok(clientService.delete(client));
    }



    @GetMapping("/{idClient}/registry")
    public ResponseEntity<Map<String,List<CallsDates>>> getCallsBetweenDates(@PathVariable Integer idClient,
                                                                       @RequestParam(name = "dateFrom") String dateFrom,
                                                                       @RequestParam(name = "dateTo") String dateTo) throws ClientNotFoundException, ParseException, ValidationException {

        Client client = this.clientService.getById(idClient);
        Date from = new SimpleDateFormat("yyyy/MM/dd").parse(dateFrom);
        Date to = new SimpleDateFormat("yyyy/MM/dd").parse(dateTo);
        Map<String, List<CallsDates>> callsBetweenDates = callService.getCallsBetweenDates(client.getId(), from, to);

        return (!callsBetweenDates.isEmpty()) ? ResponseEntity.ok(callsBetweenDates) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
