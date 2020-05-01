package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.FareMySQLDao;
import org.springframework.beans.factory.annotation.Autowired;

public class FareService {
    private FareMySQLDao fareMySQLDao;

    @Autowired
    public FareService(FareMySQLDao fareMySQLDao) {
        this.fareMySQLDao = fareMySQLDao;
    }
}
