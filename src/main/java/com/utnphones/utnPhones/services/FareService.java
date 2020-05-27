package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.FareMySQLDao;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.projections.FareByCities;
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

    public List<Fare> getAll(){
        return this.fareRepository.findAll();
    }

    public Fare create(Fare fare){
        return this.fareRepository.save(fare);
    }

    public FareByCities getFareByCities(Integer idCityFrom, Integer idCityTo){
        return this.fareRepository.getFareByCities(idCityFrom, idCityTo);
    }
}
