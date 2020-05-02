package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.services.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    public List<Client> getAll(){
        return this.clientService.getAll();
    }
}
