package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.CityMySQLDao;
import org.springframework.beans.factory.annotation.Autowired;

public class CityService {
    private CityMySQLDao cityMySQLDao;

    @Autowired
    public CityService(final CityMySQLDao cityMySQLDao) {
        this.cityMySQLDao = cityMySQLDao;
    }
}
