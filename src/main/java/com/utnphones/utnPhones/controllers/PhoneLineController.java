package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotIsAlreadyDeletedException;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PhoneLineController {
    private PhoneLineService phoneLineService;
    private ClientService clientService;

    @Autowired
    public PhoneLineController(final PhoneLineService phoneLineService,
                               final ClientService clientService) {
        this.phoneLineService = phoneLineService;
        this.clientService = clientService;
    }

    public PhoneLine create(Integer idClient, PhoneLine phoneLine) throws ClientNotFoundException {
        phoneLine.setClient(this.clientService.getById(idClient));
        return this.phoneLineService.create(phoneLine);
    }


    public PhoneLine getById(Integer idPhoneLine) throws PhoneLineNotFoundException {
        return this.phoneLineService.getById(idPhoneLine);
    }

    public void delete(Integer idPhoneLine) throws PhoneLineNotFoundException, PhoneLineNotIsAlreadyDeletedException {
        phoneLineService.delete(idPhoneLine);
    }

/*
    public PhoneLine updatePhoneLine(Integer idPhoneline, PhoneLineDto phoneLine) throws PhoneLineNotFoundException {

    }*/
}
