package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {
    private CallService callService;

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
    }
}
