package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

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

    public void create(CallDto callDto) throws PhoneLineNotFoundException {

        PhoneLine numberFrom = phoneLineService.getByPhoneNumber(callDto.getNumberFrom());
        PhoneLine numberTo = phoneLineService.getByPhoneNumber(callDto.getNumberTo());

        //return callRepository.saveCall(idNumberFrom,idNumberTo,callDto.getDuration(),call.getDate());

        //Dos consultas a la bdd para obtener los objetos enteros de phoneline justifican esto?
        callRepository.save(Call.builder()
                        .phoneFrom(numberFrom)
                        .phoneTo(numberTo)
                        .duration(callDto.getDuration())
                        .date(callDto.getDate())
                        .build());

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
