package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.CallMySQLDao;
import com.utnphones.utnPhones.domain.Call;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class CallService {
    private CallMySQLDao callMySQLDao;

    @Autowired
    public CallService(final CallMySQLDao callMySQLDao) {
        this.callMySQLDao = callMySQLDao;
    }

    public List<Call> getAll(){
        return this.callMySQLDao.getAll();
    }
}
