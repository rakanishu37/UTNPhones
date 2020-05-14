package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.CallMySQLDao;
import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallService {
    private CallRepository callRepository;

    @Autowired
    public CallService(CallRepository callRepository) {
        this.callRepository = callRepository;
    }

    /** @Autowired
    public CallService(final CallMySQLDao callMySQLDao) {
        this.callMySQLDao = callMySQLDao;
    }*/

    public List<Call> getAll(){
        return this.callRepository.findAll();
    }

    public Call create(Call call){
        Call c = this.callRepository.save(call);
        System.out.println(c.toString());
        return this.callRepository.save(call);
    }
}
