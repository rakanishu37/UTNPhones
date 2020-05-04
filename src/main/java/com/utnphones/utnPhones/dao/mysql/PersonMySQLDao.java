package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.interfaces.PersonDao;
import com.utnphones.utnPhones.domain.*;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
public class PersonMySQLDao implements PersonDao {
    private Connection connection;
    private CityMySQLDao cityMySQLDao;


    public PersonMySQLDao(Connection connection, CityMySQLDao cityMySQLDao) {
        this.connection = connection;
        this.cityMySQLDao = cityMySQLDao;
    }

    @Override
    public Person add(Person value) {
        return null;
    }

    @Override
    public Person update(Person value) {
        return null;
    }

    @Override
    public void remove(Integer id) {

    }

    @Override
    public void remove(Person value) {

    }

    @Override
    public Person getById(Integer id) {
        return null;
    }

    @Override
    public List<Person> getAll() {
        List<Person> personList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(MySQLUtils.GET_ALL_PERSONS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personList.add(
                        resultSet.getInt(8) == 1 ?
                                Client.builder().id(resultSet.getInt(1))
                                        .city(this.cityMySQLDao.getById(resultSet.getInt(2)))
                                        .firstname(resultSet.getString(3))
                                        .surname(resultSet.getString(4))
                                        .DNI(resultSet.getString(5))
                                        .username(resultSet.getString(6))
                                        .password(resultSet.getString(7))
                                        .phoneLines(new ArrayList<>()).build()
                                :
                                Employee.builder().id(resultSet.getInt(1))
                                        .city(this.cityMySQLDao.getById(resultSet.getInt(2)))
                                        .firstname(resultSet.getString(3))
                                        .surname(resultSet.getString(4))
                                        .DNI(resultSet.getString(5))
                                        .username(resultSet.getString(6))
                                        .password(resultSet.getString(7)).build()
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }
}
