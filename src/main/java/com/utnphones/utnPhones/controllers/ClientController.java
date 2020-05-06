package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public List<Client> getAll(){
        return this.clientService.getAll();
    }

}
