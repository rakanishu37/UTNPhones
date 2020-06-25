package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.FareNotFoundException;
import com.utnphones.utnPhones.repository.FareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FareService {
    private FareRepository fareRepository;

    @Autowired
    public FareService(final FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }

    public Fare getFareByCities(Integer idCityFrom, Integer idCityTo) throws CityNotFoundException {
        return fareRepository.getFareByCities(idCityFrom, idCityTo).orElseThrow(CityNotFoundException::new);
    }


}
