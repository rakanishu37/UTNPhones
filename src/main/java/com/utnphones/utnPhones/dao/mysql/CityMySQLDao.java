package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.CityDao;
import com.utnphones.utnPhones.domain.City;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class CityMySQLDao implements CityDao {
    private Connection connection;

    @Override
    public City add(City value) {
        return null;
    }

    @Override
    public City update(City value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(City value) {

    }

    @Override
    public City getById(Integer id) {
        return null;
    }

    @Override
    public List<City> getAll() {
        return null;
    }
}
