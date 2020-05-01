package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.services.LineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineTypeController {
    private LineTypeService lineTypeService;

    @Autowired
    public LineTypeController(final LineTypeService lineTypeService) {
        this.lineTypeService = lineTypeService;
    }
}
