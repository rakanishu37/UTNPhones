package com.utnphones.utnPhones.dao.mysql;

import com.utnphones.utnPhones.dao.PersonDao;
import com.utnphones.utnPhones.domain.Person;
import lombok.Data;

import java.sql.Connection;
import java.util.List;

@Data
public class PersonMySQLDao implements PersonDao {
    private Connection connection;

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
        return null;
    }
}
