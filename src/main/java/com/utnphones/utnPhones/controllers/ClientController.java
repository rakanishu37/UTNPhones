package com.utnphones.utnPhones.controllers;


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
    public Client getById(@PathVariable Integer idClient) /*throws ClientNotFoundException */{
        return this.clientService.getById(idClient).get();

        /*if(!client.isPresent()){
            client.orElseThrow(() -> new ClientNotFoundException("CLient not found"));
        }
        return client.get();

        */

    }

    @PostMapping("/")
    public Client create(@RequestBody Client client){
        return this.clientService.create(client);
    }

    @PostMapping("/{idClient}/phoneline")
    public PhoneLine createPhoneLine(@PathVariable Integer idClient, @RequestBody PhoneLine phoneLine){
        phoneLine.setClient(this.clientService.getById(idClient).get());
        return this.phoneLineService.create(phoneLine);
    }

}
