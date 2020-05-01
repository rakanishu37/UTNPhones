package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.PhoneLineMySQLDao;
import org.springframework.beans.factory.annotation.Autowired;

public class PhoneLineService {
    private PhoneLineMySQLDao phoneLineMySQLDao;

    @Autowired
    public PhoneLineService(final PhoneLineMySQLDao phoneLineMySQLDao) {
        this.phoneLineMySQLDao = phoneLineMySQLDao;
    }
}
