package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClientController {
    private ClientService clientService;
    private SessionManager sessionManager;

    @Autowired
    public ClientController(ClientService clientService, SessionManager sessionManager) {
        this.clientService = clientService;
        this.sessionManager = sessionManager;
    }

    public List<Client> getAll(Integer quantity, Integer from){
        return this.clientService.getAll(quantity,from);
    }

    public Client getById(Integer id) throws ClientNotFoundException {
        return this.clientService.getById(id);
    }

    public Client create(ClientCreatedDTO client) throws CityNotFoundException {
        return this.clientService.create(client);
    }

    public Client update(Integer id, ClientUpdatedDTO client) throws ClientNotFoundException, CityNotFoundException {

        return this.clientService.update(id, client);
    }


    public Integer delete(Integer id) throws ClientIsAlreadyDeletedException, ClientNotFoundException {
        return this.clientService.delete(id);
    }

    public PhoneLine createPhoneLine(Integer idClient, PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(clientService.getById(idClient));
        return clientService.setPhoneline(phoneLine);
    }

}
