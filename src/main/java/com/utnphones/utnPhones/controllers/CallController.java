package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.dto.TopTenDestinies;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class CallController {
    private CallService callService;

    @Autowired
    public CallController(final CallService callService) {
        this.callService = callService;
    }

    public Call create(CallDto callDto) throws PhoneLineNotFoundException {
        return callService.create(callDto);
    }


    public Map<String, List<CallsDates>> getAllByClient(Integer idClient, String dateFrom, String dateTo, Integer from, Integer quantity) throws ParseException {
        return this.callService.getCalls(idClient, dateFrom, dateTo, from, quantity);
    }

    public TopTenDestinies getTopTenDestiniesByClient(Integer idClient){
        return this.callService.getTopTenDestiniesByClient(idClient);
    }
}
