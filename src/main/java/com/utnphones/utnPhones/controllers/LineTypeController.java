package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.repository.LineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/linetypes")
public class LineTypeController {
    private LineTypeRepository lineTypeRepository;

    @Autowired
    public LineTypeController(final LineTypeRepository lineTypeRepository) {
        this.lineTypeRepository = lineTypeRepository;
    }
}
