package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//todo borrar
@Service
public class ProvinceService {
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(final ProvinceRepository provinceRepository){
        this.provinceRepository = provinceRepository;
    }
    public List<Province> getAll(){
        return this.provinceRepository.findAll();
    }

    public Province create(Province province){
        return this.provinceRepository.save(province);
    }

    public Province getById(Integer id) throws Exception {
        return this.provinceRepository.findById(id).orElseThrow(Exception::new);
    }
}
