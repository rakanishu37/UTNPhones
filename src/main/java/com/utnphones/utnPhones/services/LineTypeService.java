package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.LineTypeMySQLDao;
import com.utnphones.utnPhones.repository.LineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineTypeService {
    private LineTypeMySQLDao lineTypeMySQLDao;
    private LineTypeRepository lineTypeRepository;
    /*@Autowired
    public LineTypeService(final LineTypeMySQLDao lineTypeMySQLDao) {
        this.lineTypeMySQLDao = lineTypeMySQLDao;
    }*/
    @Autowired
    public LineTypeService(final LineTypeRepository lineTypeRepository) {
        this.lineTypeRepository = lineTypeRepository;
    }
}
