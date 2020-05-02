package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.PhoneLineMySQLDao;
import com.utnphones.utnPhones.domain.PhoneLine;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PhoneLineService {
    private PhoneLineMySQLDao phoneLineMySQLDao;

    @Autowired
    public PhoneLineService(final PhoneLineMySQLDao phoneLineMySQLDao) {
        this.phoneLineMySQLDao = phoneLineMySQLDao;
    }

    public List<PhoneLine> getAll(){
        return this.phoneLineMySQLDao.getAll();
    }
}
