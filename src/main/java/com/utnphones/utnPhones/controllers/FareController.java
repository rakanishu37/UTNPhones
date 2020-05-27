package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.projections.FareByCities;
import com.utnphones.utnPhones.services.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fares")
public class FareController {
    private FareService fareService;

    @Autowired
    public FareController(final FareService fareService) {
        this.fareService = fareService;
    }

    @GetMapping("/")
    public List<Fare> getAll(){
        return this.fareService.getAll();
    }

    @PostMapping("/")
    public Fare create(@RequestBody Fare fare){
        return this.fareService.create(fare);
    }

    //todo consulta de la tarifa entre la 2 ciudades consultadas

    @GetMapping("/test")
    public FareByCities getFareByCities(@RequestParam(name = "cityFrom") Integer idCityFrom
                                            , @RequestParam(name = "cityTo") Integer idCityTo){
        return this.fareService.getFareByCities(idCityFrom, idCityTo);
    }
}
