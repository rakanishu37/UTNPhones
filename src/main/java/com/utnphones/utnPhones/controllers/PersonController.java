package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.Person;
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

    public List<Person> getAll(){
        return this.personService.getAll();
    }
}
