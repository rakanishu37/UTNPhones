package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    private CityService cityService;
    private ProvinceService provinceService;

    @Autowired
    public CityController(final CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/")
    public List<City> getAll(){
        return this.cityService.getAll();
    }

    @PostMapping("/")//Si le ponemos un id cualquiera en el post funciona bien
    public City create(@RequestBody City city){
        return this.cityService.create(city);
    }
}
