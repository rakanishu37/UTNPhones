package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.PhoneLineMySQLDao;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneLineService {
    private PhoneLineMySQLDao phoneLineMySQLDao;
    private PhoneLineRepository phoneLineRepository;

    /*@Autowired
    public PhoneLineService(final PhoneLineMySQLDao phoneLineMySQLDao) {
        this.phoneLineMySQLDao = phoneLineMySQLDao;
    }*/
    @Autowired
    public PhoneLineService(final PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    /*public List<PhoneLine> getAll(){
        return this.phoneLineMySQLDao.getAll();
    }*/
    public List<PhoneLine> getAll(){
        return this.phoneLineRepository.findAll();
    }

    public PhoneLine create(PhoneLine phoneLine){
        return this.phoneLineRepository.save(phoneLine);
    }

    public Optional<PhoneLine> getById(Integer id){
        return  this.phoneLineRepository.findById(id);
    }
}
