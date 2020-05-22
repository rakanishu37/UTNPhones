package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.ProvinceMySQLDao;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(final ProvinceRepository provinceRepository){
        this.provinceRepository = provinceRepository;
    }
    public List<Province> getAll(){
        //return this.provinceMySQLDAO.getAll();
        return this.provinceRepository.findAll();
    }

    public Province create(Province province){
        return this.provinceRepository.save(province);
    }

    public Optional<Province> getById(Integer id){
        return this.provinceRepository.findById(id);
    }
}
