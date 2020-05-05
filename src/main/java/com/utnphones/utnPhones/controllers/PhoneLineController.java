package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phoneline")
public class PhoneLineController {
    private PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(final PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    public List<PhoneLine> getAll(){
        return this.phoneLineService.getAll();
    }
}
