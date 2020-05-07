package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(final CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/")
    public List<City> getAll(){
        return this.cityService.getAll();
    }

    @PostMapping("/")
    public City create(@RequestBody City city){
        City created = this.cityService.create(city);
        System.out.println(created.toString());
        return created;
    }
}
