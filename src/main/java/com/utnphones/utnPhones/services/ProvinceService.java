package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.ProvinceMySQLDao;
import com.utnphones.utnPhones.domain.Province;

import java.util.List;

public class ProvinceService {
    private ProvinceMySQLDao provinceMySQLDAO;

    public ProvinceService(ProvinceMySQLDao provinceMySQLDAO) {
        this.provinceMySQLDAO = provinceMySQLDAO;
    }

    public List<Province> getAll(){
        return this.provinceMySQLDAO.getAll();
    }
}
