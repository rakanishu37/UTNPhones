package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CallService {
    private CallRepository callRepository;
    private PhoneLineService phoneLineService;
    @Autowired
    public CallService(CallRepository callRepository,PhoneLineService phoneLineService) {
        this.callRepository = callRepository;
        this.phoneLineService = phoneLineService;
    }


    public List<Call> getAll(){
        return this.callRepository.findAll();
    }

    public Integer create(CallDto call) throws CallNotFoundException, PhoneLineNotFoundException, ParseException {

        Integer idNumberFrom = phoneLineService.getByPhoneNumber(call.getNumberFrom()).getId();
        Integer idNumberTo = phoneLineService.getByPhoneNumber(call.getNumberTo()).getId();
        //Date date = new SimpleDateFormat("yyyy/MM/dd").parse(call.getDate());
        return callRepository.saveCall(idNumberFrom,idNumberTo,call.getDuration(),call.getDate());
    }

    public Call getById(Integer idCall) throws CallNotFoundException {
        return callRepository.findById(idCall)
                .orElseThrow(() -> new CallNotFoundException());
    }

    //Paginacion
    /*
    public List<Call> getAllByClient() {

    }*/
}
