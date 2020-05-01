package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.LineTypeMySQLDao;
import org.springframework.beans.factory.annotation.Autowired;

public class LineTypeService {
    private LineTypeMySQLDao lineTypeMySQLDao;

    @Autowired
    public LineTypeService(final LineTypeMySQLDao lineTypeMySQLDao) {
        this.lineTypeMySQLDao = lineTypeMySQLDao;
    }
}
