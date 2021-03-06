package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    public City getByName(String cityName) throws CityNotFoundException {
        return this.cityRepository.findByName(cityName).orElseThrow(CityNotFoundException::new);
    }
}
