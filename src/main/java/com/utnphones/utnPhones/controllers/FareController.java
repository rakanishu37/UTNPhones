package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.services.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fare")
public class FareController {
    private FareService fareService;

    @Autowired
    public FareController(final FareService fareService) {
        this.fareService = fareService;
    }
}
