package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotIsAlreadyDeletedException;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public PhoneLine getByPhoneNumber(String phoneNumber) throws PhoneLineNotFoundException {
        return this.phoneLineRepository.findByLineNumber(phoneNumber)
                .orElseThrow(()-> new PhoneLineNotFoundException());
    }

    public PhoneLine updatePhoneLine(PhoneLine phoneLine) {
        return phoneLineRepository.save(phoneLine);
    }

    public void delete(Integer idPhoneLine) throws PhoneLineNotFoundException, PhoneLineNotIsAlreadyDeletedException {
        PhoneLine phoneLineToBeDeleted = getById(idPhoneLine);
        if (!phoneLineToBeDeleted.getIsActive()) {
            throw new PhoneLineNotIsAlreadyDeletedException();
        }
        phoneLineRepository.deletePhoneLine(phoneLineToBeDeleted.getId());
    }
}
