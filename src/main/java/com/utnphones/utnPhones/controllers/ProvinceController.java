package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.services.ProvinceService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

   // @GetMapping("/")
    public List<Province> getAll(){
        return this.provinceService.getAll();
    }

   // @PostMapping("/")
    public Province create(@RequestBody Province province){
        return this.provinceService.create(province);
    }
}
