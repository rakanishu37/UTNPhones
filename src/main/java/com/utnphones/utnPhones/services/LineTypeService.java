package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.repository.LineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineTypeService {
    private LineTypeRepository lineTypeRepository;

    @Autowired
    public LineTypeService(final LineTypeRepository lineTypeRepository) {
        this.lineTypeRepository = lineTypeRepository;
    }
}
