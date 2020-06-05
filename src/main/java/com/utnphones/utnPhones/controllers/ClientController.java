package com.utnphones.utnPhones.controllers;


import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.UnauthorizedAccessException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.projections.InvoicesDates;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.session.Session;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.utnphones.utnPhones.utils.Constants.USER_TYPE_EMPLOYEE;

@Controller
public class ClientController {
    private ClientService clientService;
    private SessionManager sessionManager;

    @Autowired
    public ClientController(ClientService clientService, SessionManager sessionManager) {
        this.clientService = clientService;
        this.sessionManager = sessionManager;
    }

    public List<Client> getAll(Integer page){
        return this.clientService.getAll(page);
    }

    public Client getById(Integer id) throws ClientNotFoundException {
        return this.clientService.getById(id);
    }

    public Client create(Client client){
        return this.clientService.create(client);
    }

    public Client update(Integer id,Client client) throws ClientNotFoundException {

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
