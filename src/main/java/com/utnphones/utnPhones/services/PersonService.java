package com.utnphones.utnPhones.services;

import com.utnphones.utnPhones.dao.mysql.PersonMySQLDao;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.exceptions.UserNotfoundException;
import com.utnphones.utnPhones.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person login(String username, String password) throws UserNotfoundException {
        Person user = personRepository.getByUsername(username, password);
        return Optional.ofNullable(user).orElseThrow(UserNotfoundException::new);
    }
}
