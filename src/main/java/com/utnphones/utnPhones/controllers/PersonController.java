package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.exceptions.UserNotfoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.security.NoSuchAlgorithmException;

import static com.utnphones.utnPhones.utils.Constants.INVALID_USER_PASS_MESSAGE;

@Controller
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    public Person login(String username, String password) throws UserNotfoundException, ValidationException, NoSuchAlgorithmException {
        if ((username != null) && (password != null)) {
            return personService.login(username, password);
        } else {
            throw new ValidationException(INVALID_USER_PASS_MESSAGE);
        }
    }
}
