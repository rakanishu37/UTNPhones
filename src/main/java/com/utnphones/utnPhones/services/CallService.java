package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.dto.PageableResponse;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    public List<Call> getAll(Integer to, Integer from, Date dateFrom, Date dateTo){


        if(dateFrom==null || dateTo==null){
            return this.callRepository.findAll(to, from);
        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = simpleDateFormat.format(dateFrom);
            String toDate = simpleDateFormat.format(dateTo);
            return this.callRepository.findAll(to, from, dateFrom, dateTo);
        }

    }

    public URI create(CallDto callDto) throws PhoneLineNotFoundException {

        PhoneLine numberFrom = phoneLineService.getByPhoneNumber(callDto.getNumberFrom());
        PhoneLine numberTo = phoneLineService.getByPhoneNumber(callDto.getNumberTo());

        Call created = callRepository.save(Call.builder()
                        .phoneFrom(numberFrom)
                        .phoneTo(numberTo)
                        .duration(callDto.getDuration())
                        .date(callDto.getDate())
                        .build());
         return getLocation(created);
    }

    public Call getById(Integer idCall) throws CallNotFoundException {
        return callRepository.findById(idCall)
                .orElseThrow(() -> new CallNotFoundException());
    }


    public List<Call> getAllByClient(Integer id) {
        return this.callRepository.getAllCallByClient(id);
    }

    private URI getLocation(Call call) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(call.getId())
                .toUri();
    }
}
