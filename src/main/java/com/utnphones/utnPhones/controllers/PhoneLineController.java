package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/phonelines")
public class PhoneLineController {
    private PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(final PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    @GetMapping("/")
    public List<PhoneLine> getAll(){
        return this.phoneLineService.getAll();
    }

    @PostMapping("/")
    public PhoneLine create(@RequestBody PhoneLine phoneLine){
        return this.phoneLineService.create(phoneLine);
    }
}
