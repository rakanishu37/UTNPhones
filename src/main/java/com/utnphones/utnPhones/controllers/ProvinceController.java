package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @GetMapping("/")
    public List<Province> getAll(){
        return this.provinceService.getAll();
    }

    @PostMapping("/")
    public Province create(@RequestBody Province province){
        return this.provinceService.create(province);
    }
}
