package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.CityMySQLDao;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class CityService {
    private CityMySQLDao cityMySQLDao;
    private CityRepository cityRepository;
    /*@Autowired
    public CityService(final CityMySQLDao cityMySQLDao) {
        this.cityMySQLDao = cityMySQLDao;
    }*/
    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAll(){
        return this.cityRepository.findAll();
    }

    public City create(City city){
        return this.cityRepository.save(city);

    }
}