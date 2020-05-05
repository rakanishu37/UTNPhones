package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.FareMySQLDao;
import com.utnphones.utnPhones.repository.FareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FareService {
    private FareMySQLDao fareMySQLDao;
    private FareRepository fareRepository;
    /*@Autowired
    public FareService(final FareMySQLDao fareMySQLDao) {
        this.fareMySQLDao = fareMySQLDao;
    }*/
    @Autowired
    public FareService(final FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }
}
