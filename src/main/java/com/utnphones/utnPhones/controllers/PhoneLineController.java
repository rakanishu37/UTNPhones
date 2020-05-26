package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/{idPhoneLine}")
    public PhoneLine getById(@PathVariable Integer idPhoneLine) throws PhoneLineNotFoundException {
        return this.phoneLineService.getById(idPhoneLine);
    }

    @PutMapping("/{idPhoneline}/activate")
    public ResponseEntity<Integer> activatePhoneLine(@PathVariable Integer idPhoneline){
        return ResponseEntity.ok(phoneLineService.activatePhoneLine(idPhoneline));
    }

    @PutMapping("/{idPhoneline}/suspend")
    public ResponseEntity<Integer> suspendPhoneLine(@PathVariable Integer idPhoneline){
        return ResponseEntity.ok(phoneLineService.suspendPhoneLine(idPhoneline));
    }

    @PutMapping("/{idPhoneline}/cancel")
    public ResponseEntity<Integer> cancelPhoneLine(@PathVariable Integer idPhoneline){
        return ResponseEntity.ok(phoneLineService.cancelPhoneLine(idPhoneline));
    }
}
