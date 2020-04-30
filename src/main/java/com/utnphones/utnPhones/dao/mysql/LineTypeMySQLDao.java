package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.LineTypeDao;
import com.utnphones.utnPhones.domain.LineType;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class LineTypeMySQLDao implements LineTypeDao {
    private Connection connection;

    @Override
    public LineType add(LineType value) {
        return null;
    }

    @Override
    public LineType update(LineType value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(LineType value) {

    }

    @Override
    public LineType getById(Integer id) {
        return null;
    }

    @Override
    public List<LineType> getAll() {
        return null;
    }
}
