package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.InvoicesDates;
import com.utnphones.utnPhones.projections.PersonDuration;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/calls")
public class CallController {
    private CallService callService;
    private SessionManager sessionManager;

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
    }

    @GetMapping("/")
    public List<Call> getAll(){
        return this.callService.getAll();
    }

    @PostMapping("/")
    public Integer create(@RequestBody CallDto call) throws CallNotFoundException, PhoneLineNotFoundException, ParseException {
        return this.callService.create(call);
    }

    @GetMapping("")
    public ResponseEntity<List<PersonDuration>> getPersonDurationInMonth(@RequestParam(name = "yearMonth") String date) throws ParseException {
        List<PersonDuration> list = callService.getDurationInMonth(date);
        return (!list.isEmpty()) ? ResponseEntity.ok(list) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
