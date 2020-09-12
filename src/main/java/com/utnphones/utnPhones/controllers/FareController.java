package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.FareNotFoundException;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class FareController {
    private FareService fareService;
    private CityService cityService;

    @Autowired
    public FareController(final FareService fareService, final CityService cityService) {
        this.fareService = fareService;
        this.cityService = cityService;
    }

    public Fare getFareByCities(String nameCityFrom, String nameCityTo) throws CityNotFoundException, FareNotFoundException {
        City cityFrom = this.cityService.getByName(nameCityFrom);
        City cityTo = this.cityService.getByName(nameCityTo);
        return this.fareService.getFareByCities(cityFrom.getId(), cityTo.getId());
    }
}
