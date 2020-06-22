package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.exceptions.LineTypeNotFoundException;
import com.utnphones.utnPhones.repository.LineTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineTypeService {
    private final LineTypeRepository lineTypeRepository;

    public LineType findByName(String lineTypeName) throws LineTypeNotFoundException {
        return lineTypeRepository.findByTypeName(lineTypeName)
                .orElseThrow(LineTypeNotFoundException::new);
    }
}
