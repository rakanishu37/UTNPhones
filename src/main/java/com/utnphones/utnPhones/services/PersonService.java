package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.PersonMySQLDao;
import com.utnphones.utnPhones.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PersonService {
    private PersonMySQLDao personMySQLDao;

    @Autowired
    public PersonService(final PersonMySQLDao personMySQLDao) {
        this.personMySQLDao = personMySQLDao;
    }

    public List<Person> getAll(){
        return this.personMySQLDao.getAll();
    }
}
