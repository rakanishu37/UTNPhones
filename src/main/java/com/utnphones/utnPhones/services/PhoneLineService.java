package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.PhoneLineMySQLDao;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.sound.sampled.Line;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneLineService {
    private PhoneLineRepository phoneLineRepository;

    @Autowired
    public PhoneLineService(final PhoneLineRepository phoneLineRepository) {
        this.phoneLineRepository = phoneLineRepository;
    }

    public List<PhoneLine> getAll(){
        return this.phoneLineRepository.findAll();
    }

    public PhoneLine create(PhoneLine phoneLine){
        return this.phoneLineRepository.save(phoneLine);
    }

    public PhoneLine getById(Integer id) throws PhoneLineNotFoundException {
        return this.phoneLineRepository.findById(id)
                .orElseThrow(()-> new PhoneLineNotFoundException());
    }

    public PhoneLine updatePhoneLine(PhoneLine phoneLine) {
        return phoneLineRepository.save(phoneLine);
    }
}
