package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.CityDao;
import com.utnphones.utnPhones.domain.*;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class CityMySQLDao implements CityDao {
    private Connection connection;
    private ProvinceMySQLDao provinceMySQLDao;

    public CityMySQLDao(Connection connection, ProvinceMySQLDao provinceMySQLDao) {
        this.connection = connection;
        this.provinceMySQLDao = provinceMySQLDao;
    }

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
        City city = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from cities where" +
                    " cities.id_city = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
               city = new City(resultSet.getInt(1), this.provinceMySQLDao.getById(resultSet.getInt(2)), resultSet.getString(3), resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

    @Override
    public List<City> getAll() {
        return null;
    }
}
