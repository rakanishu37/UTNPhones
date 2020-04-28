package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.ProvinceMySQLDAO;
import com.utnphones.utnPhones.domain.Province;

import java.util.List;

public class ProvinceService {
    private ProvinceMySQLDAO provinceMySQLDAO;

    public ProvinceService(ProvinceMySQLDAO provinceMySQLDAO) {
        this.provinceMySQLDAO = provinceMySQLDAO;
    }

    public List<Province> getAll(){
        return this.provinceMySQLDAO.getAll();
    }
}
