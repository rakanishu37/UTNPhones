package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calls")
public class CallController {
    private CallService callService;

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
    }

    @GetMapping("/")
    public List<Call> getAll(){
        return this.callService.getAll();
    }

    @PostMapping("/")
    public Call create(@RequestBody Call call){
        return this.callService.create(call);
    }

}
