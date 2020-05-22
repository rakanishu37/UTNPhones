package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.PhoneLineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private ClientService clientService;
    private PhoneLineService phoneLineService;

    @Autowired
    public ClientController(ClientService clientService, PhoneLineService phoneLineService) {
        this.clientService = clientService;
        this.phoneLineService = phoneLineService;
    }

    @GetMapping("/")
    public List<Client> getAll(){
        return this.clientService.getAll();
    }

    @GetMapping("/{idClient}")
    public Client getById(@PathVariable Integer idClient) throws ClientNotFoundException {
        return this.clientService.getById(idClient);
    }

    @PostMapping("/")
    public Client create(@RequestBody Client client){
        return this.clientService.create(client);
    }

    @PostMapping("/{idClient}/phonelines")
    public PhoneLine createPhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(this.clientService.getById(idClient));
        return this.phoneLineService.create(phoneLine);
    }

    @PutMapping("/{idClient}/phonelines")
    //todo 200 en duda
    public PhoneLine updatePhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(this.clientService.getById(idClient));
        return phoneLineService.updatePhoneLine(phoneLine);
    }

    @DeleteMapping("/{idClient}/phonelines")
    //todo 200
    public Integer deletePhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(this.clientService.getById(idClient));
        return phoneLineService.deletePhoneLine(phoneLine);
    }

    @PutMapping("/{idClient}")
    //todo 200 en duda
    public Client updateClient(@PathVariable Integer idClient, @RequestBody Client updatedClient) throws ClientNotFoundException {
        this.clientService.getById(idClient);
        return clientService.update(updatedClient);
    }

    @DeleteMapping("/{idClient}")
    public Integer deleteClient(@PathVariable Integer idClient) throws ClientNotFoundException {
        Client client = this.clientService.getById(idClient);
        return clientService.delete(client);
    }


    /*@GetMapping("/{idClient}/registry?dateFrom=xxxx&dateTo=XXXXX")
    public ResponseEntity<Map<String,List<Call>>(el token @RequestParam(required = false))*/
}
