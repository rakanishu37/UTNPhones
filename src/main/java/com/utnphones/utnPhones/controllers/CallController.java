package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.TopTenDestinies;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    public List<CallsDates> getAllRange(Integer to, Integer from, String dateFrom, String dateTo) throws ParseException {
        return this.callService.getAll(to, from, dateFrom, dateTo);
    }

    public Map<String, List<CallsDates>> getCallsBetweenDates(Integer idClient, Date from, Date to) {

        return this.callService.getCallsBetweenDates(idClient, from, to);
    }

    public Map<String, List<CallsDates>> getAllByClient(Integer id){

        return this.callService.getAllByClient(id);
    }

    public List<TopTenDestinies> getTopTenDestiniesByClient(Integer idClient){
        return this.callService.getTopTenDestiniesByClient(idClient);
    }
}
