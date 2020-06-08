package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.FareNotFoundException;
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

    @Autowired
    public FareController(final FareService fareService) {
        this.fareService = fareService;
    }

    //@GetMapping("/")
    public List<Fare> getAll(){
        return this.fareService.getAll();
    }

   // @PostMapping("/")
    public Fare create(@RequestBody Fare fare){
        return this.fareService.create(fare);
    }

   // @GetMapping("/{idFare}")
    public ResponseEntity<Fare> getById(@PathVariable Integer idFare) throws FareNotFoundException {
        return ResponseEntity.ok(fareService.getById(idFare));
    }
    //TODO getByCity
   // @GetMapping("")
    public Fare getFareByCities(Integer idCityFrom, Integer idCityTo) throws CityNotFoundException {
        return this.fareService.getFareByCities(idCityFrom, idCityTo);
    }
}
