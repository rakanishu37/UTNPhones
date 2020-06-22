package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotIsAlreadyDeletedException;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneLineService {
    private PhoneLineRepository phoneLineRepository;
    private LineTypeService lineTypeService;

    @Autowired
    public PhoneLineService(final PhoneLineRepository phoneLineRepository, final LineTypeService lineTypeService) {
        this.phoneLineRepository = phoneLineRepository;
        this.lineTypeService = lineTypeService;
    }

    public List<PhoneLine> getAll(){
        return this.phoneLineRepository.findAllByIsActive(Boolean.TRUE);
    }

    public PhoneLine create(PhoneLineDTO phoneLineDTO, Client client, LineType lineType, City city) {
        PhoneLine phoneLine = PhoneLine.builder()
                .client(client)
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(lineType)
                .build();

        return this.phoneLineRepository.save(phoneLine);
    }

    public PhoneLine getById(Integer id) throws PhoneLineNotFoundException {
        return this.phoneLineRepository.findByIdAndIsActive(id,Boolean.TRUE)
                .orElseThrow(()-> new PhoneLineNotFoundException());
    }


    public PhoneLine getByPhoneNumber(String phoneNumber) throws PhoneLineNotFoundException {
        return this.phoneLineRepository.findByLineNumberAndIsActive(phoneNumber,Boolean.TRUE)
                .orElseThrow(()-> new PhoneLineNotFoundException());
    }

    public PhoneLine updatePhoneLine(Integer idPhoneline, PhoneLineDTO phoneLineDTO, City city, LineType lineType) throws PhoneLineNotFoundException {
        return phoneLineRepository.findById(idPhoneline)
                .map(phoneLine -> update(phoneLine,phoneLineDTO,city,lineType))
                .map(phoneLineRepository::save)
                .orElseThrow(PhoneLineNotFoundException::new);
    }

    private PhoneLine update(PhoneLine phoneLine, PhoneLineDTO phoneLineDTO,City city,LineType lineType){
        phoneLine.setLineNumber(city.getPrefix() + phoneLineDTO.getLineNumber());
        phoneLine.setLineType(lineType);
        phoneLine.setLineStatus(phoneLineDTO.getStatus());
        return phoneLine;
    }

    public void delete(Integer idPhoneLine) throws PhoneLineNotFoundException{
        PhoneLine phoneLine = getById(idPhoneLine);
        phoneLine.setIsActive(Boolean.FALSE);
        phoneLineRepository.save(phoneLine);
    }

}
