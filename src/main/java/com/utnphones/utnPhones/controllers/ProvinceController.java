package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.services.ProvinceService;

import java.util.List;

public class ProvinceController {
    private ProvinceService provinceService;

    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    public List<Province> getAll(){
        return this.provinceService.getAll();
    }
    
}
