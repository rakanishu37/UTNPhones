package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProvinceController {
    private ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    public List<Province> getAll(){
        return this.provinceService.getAll();
    }
}
