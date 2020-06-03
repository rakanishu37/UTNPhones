package com.utnphones.utnPhones.controllers.web;

import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AntennaController {

   /*@PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CallDto call){
        ResponseEntity responseEntity;
        try {
            callService.create(call);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (PhoneLineNotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return responseEntity;
    }*/
}
