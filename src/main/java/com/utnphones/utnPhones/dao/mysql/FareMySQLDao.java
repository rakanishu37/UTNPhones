package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.FareDao;
import com.utnphones.utnPhones.domain.Fare;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class FareMySQLDao implements FareDao {
    private Connection connection;

    @Override
    public Fare add(Fare value) {
        return null;
    }

    @Override
    public Fare update(Fare value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Fare value) {

    }

    @Override
    public Fare getById(Integer id) {
        return null;
    }

    @Override
    public List<Fare> getAll() {
        return null;
    }
}
