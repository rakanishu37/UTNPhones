package com.utnphones.utnPhones.controllers.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.utils.UriGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/antenna")
public class AntennaController {
    private CallController callController;

    @Autowired
    public AntennaController(CallController callController) {
        this.callController = callController;
    }

    @PostMapping("/calls")
    public ResponseEntity<?> create(@RequestBody CallDto callDto){
        ResponseEntity responseEntity;

        try {
            URI uri = UriGenerator.getLocation(this.callController.create(callDto).getId());
            responseEntity = ResponseEntity.created(uri).build();
        } catch (PhoneLineNotFoundException e) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return responseEntity;
    }

}
