package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.PersonController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.exceptions.UserNotfoundException;
import com.utnphones.utnPhones.exceptions.ValidationException;
import com.utnphones.utnPhones.services.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersonControllerTest {
    @Mock
    private PersonService personService;

    private PersonController personController;

    @Before
    public void setUp() {
        initMocks(this);
        this.personController = new PersonController(personService);
    }

    @Test
    public void callLoginOk() throws UserNotfoundException, NoSuchAlgorithmException, ValidationException {
        String username = "user";
        String password = "pass";

        Person person = Person.builder()
                .id(123)
                .city(new City())
                .DNI("37753328")
                .username(username)
                .password(password)
                .firstname("test")
                .surname("test")
                .userType(new UserType())
                .build();

        when(personService.login(username, password)).thenReturn(person);

        Person test = personController.login(username, password);
        assertEquals(username, test.getUsername());
        assertEquals(password, test.getPassword());
    }

    @Test(expected = ValidationException.class)
    public void callLoginWithNullParameters() throws UserNotfoundException, NoSuchAlgorithmException, ValidationException {
        Person test = personController.login(null, null);
    }

    @Test(expected = UserNotfoundException.class)
    public void callLoginThenThrowUserNotFound() throws UserNotfoundException, NoSuchAlgorithmException, ValidationException {
        String username = "user";
        String password = "pass";
        when(personService.login(username, password)).thenThrow(new UserNotfoundException());

        Person test = personController.login(username, password);
    }



}
