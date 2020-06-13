package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.LineTypeNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotIsAlreadyDeletedException;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.LineTypeService;
import com.utnphones.utnPhones.services.PhoneLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PhoneLineController {
    private PhoneLineService phoneLineService;
    private ClientService clientService;
    private LineTypeService lineTypeService;
    private CityService cityService;
    @Autowired
    public PhoneLineController(final PhoneLineService phoneLineService, final ClientService clientService,
                               final LineTypeService lineTypeService, final CityService cityService) {
        this.phoneLineService = phoneLineService;
        this.clientService = clientService;
        this.lineTypeService = lineTypeService;
        this.cityService = cityService;
    }

    public PhoneLine create(Integer idClient, PhoneLineDTO phoneLineDTO) throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        Client client = clientService.getById(idClient);
        City city = cityService.getByName(phoneLineDTO.getCityName());
        LineType lineType = lineTypeService.findByName(phoneLineDTO.getLineType());
        return this.phoneLineService.create(phoneLineDTO,client,lineType,city);
    }


    public PhoneLine getById(Integer idPhoneLine) throws PhoneLineNotFoundException {
        return this.phoneLineService.getById(idPhoneLine);
    }

    public void delete(Integer idPhoneLine) throws PhoneLineNotFoundException, PhoneLineNotIsAlreadyDeletedException {
        phoneLineService.delete(idPhoneLine);
    }


    public PhoneLine updatePhoneLine(Integer idPhoneline, PhoneLineDTO phoneLineDTO) throws PhoneLineNotFoundException, CityNotFoundException, LineTypeNotFoundException {
        City city = cityService.getByName(phoneLineDTO.getCityName());
        LineType lineType = lineTypeService.findByName(phoneLineDTO.getLineType());

        return phoneLineService.updatePhoneLine(idPhoneline,phoneLineDTO,city,lineType);
    }


}
