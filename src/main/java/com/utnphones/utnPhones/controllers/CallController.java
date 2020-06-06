package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.dto.PageableResponse;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.net.URI;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class CallController {
    private CallService callService;
    private SessionManager sessionManager;

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
        this.sessionManager = sessionManager;
    }

    public URI create(CallDto callDto) throws PhoneLineNotFoundException {

        return this.callService.create(callDto);
    }

    public List<Call> getAllRange(Integer to, Integer from){
        return this.callService.getAllRange(to, from);


    public Map<String, List<CallsDates>> getCallsBetweenDates(Integer idClient, Date from, Date to) {
        return callService.
    }
    public List<Call> getAll(Integer to, Integer from){
        return this.callService.getAll(to, from);
    }

    public List<Call> getAllByClient(Integer id){
        return this.callService.getAllByClient(id);
    }


>>>>>>> e0825604494cc3c3b6648e0fcf90ba42889d5576
}
