package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.exceptions.UserNotfoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public Person login(String username, String password) throws UserNotfoundException, ValidationException {
        if ((username != null) && (password != null)) {
            return personService.login(username, password);
        } else {
            throw new ValidationException("username and password must have a value");
        }
    }
}
