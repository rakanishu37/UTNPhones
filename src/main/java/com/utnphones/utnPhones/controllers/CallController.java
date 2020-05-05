package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/call")
public class CallController {
    private CallService callService;

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
    }

    public List<Call> getAll(){
        return this.callService.getAll();
    }
}
