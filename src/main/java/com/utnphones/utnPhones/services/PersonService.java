package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.PersonMySQLDao;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private PersonMySQLDao personMySQLDao;
    private PersonRepository personRepository;
    /*@Autowired
    public PersonService(final PersonMySQLDao personMySQLDao) {
        this.personMySQLDao = personMySQLDao;
    }*/
    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAll(){
        return this.personMySQLDao.getAll();
    }
}
