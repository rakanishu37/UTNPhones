package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public CallController(final CallService callService, final SessionManager sessionManager) {
        this.callService = callService;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/{page}")
    public ResponseEntity<List<Call>> getAll(@RequestHeader("Authorization") String token, @PathVariable Integer page) throws UserNotLoggedException {
        Person person = this.sessionManager.getCurrentUser(token);
        
        List<Call> calls = this.callService.getAll(page);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CallDto call){
        ResponseEntity responseEntity;
        try {
            callService.create(call);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PhoneLineNotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return responseEntity;
    }

    //Paginacion para esto, un dto con el id del cliente y que pagina y cantidad de registros a devolver
    /*@GetMapping("/")  calls?idClient=654
    public List<Call> getAllByClient(){
        return callService.getAllByClient();
    }*/

}
