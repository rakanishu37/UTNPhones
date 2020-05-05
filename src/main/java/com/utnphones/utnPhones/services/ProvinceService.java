package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.ProvinceMySQLDao;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    //private ProvinceMySQLDao provinceMySQLDAO;
    private final ProvinceRepository provinceRepository;

    /*@Autowired
    public ProvinceService(final ProvinceMySQLDao provinceMySQLDAO) {
        this.provinceMySQLDAO = provinceMySQLDAO;
    }*/
    @Autowired
    public ProvinceService(final ProvinceRepository provinceRepository){
        this.provinceRepository = provinceRepository;
    }
    public List<Province> getAll(){
        //return this.provinceMySQLDAO.getAll();
        return this.provinceRepository.findAll();
    }
}
