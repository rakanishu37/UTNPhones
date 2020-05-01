package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneLineController {
    private PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(final PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }
}
