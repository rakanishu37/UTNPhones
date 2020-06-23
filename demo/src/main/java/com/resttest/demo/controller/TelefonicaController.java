package com.resttest.demo.controller;

import com.resttest.demo.models.Call;
import com.resttest.demo.service.IntegrationService;
import com.resttest.demo.service.TelefonicaService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/telefonicas")
public class TelefonicaController {

    private IntegrationService integrationService;

    @Autowired
    public TelefonicaController(IntegrationService integrationService){
        this.integrationService = integrationService;
    }

    @GetMapping("/calls")
    public ResponseEntity<List<Call>> getCalls(){
        List<Call> calls = this.integrationService.getCalls();
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/hola")
    public void getHola(){
            this.integrationService.test();
    }



}
