package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.CallMySQLDao;
import org.springframework.beans.factory.annotation.Autowired;


public class CallService {
    private CallMySQLDao callMySQLDao;

    @Autowired

    public CallService(final CallMySQLDao callMySQLDao) {
        this.callMySQLDao = callMySQLDao;
    }
}
