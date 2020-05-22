package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CallService {
    private CallRepository callRepository;

    @Autowired
    public CallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }


    public List<Call> getAll(){
        return this.callRepository.findAll();
    }

    public Call create(Call call){
        Call c = this.callRepository.save(call);
        System.out.println(c.toString());
        return this.callRepository.save(call);
    }

    public Call getById(Integer idCall) throws CallNotFoundException {
        return callRepository.findById(idCall)
                .orElseThrow(() -> new CallNotFoundException("Client not found"));
    }

    public Map<String, List<CallsDates>> getCallsBetweenDates(Integer idClient, Date from, Date to){
        List<CallsDates> calls = callRepository.getCallsBetweenDates(idClient,from,to);

        return calls.stream()
            .collect(Collectors.groupingBy(CallsDates::getPhoneLineFrom));

    }
}
