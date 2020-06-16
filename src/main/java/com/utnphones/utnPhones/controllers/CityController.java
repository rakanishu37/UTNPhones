package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(final CityService cityService) {
        this.cityService = cityService;
    }

    public List<City> getAll(){
        return this.cityService.getAll();
    }

    public City create(@RequestBody City city){
        return this.cityService.create(city);
    }
}
