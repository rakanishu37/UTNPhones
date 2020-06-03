package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.PhoneLineService;
import com.utnphones.utnPhones.session.SessionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientControllerTest {

    ClientController clientController;

    @Mock
    ClientService clientService;
    SessionManager sessionManager;
    @Before
    public void setUp(){
        initMocks(this);
        clientController = new ClientController(clientService,sessionManager);
    }
/*
    @Test(expected = ClientNotFoundException.class)
    public void testGetClientByIdNotFound() throws ClientNotFoundException {
       when(clientService.getById(15)).thenThrow(new ClientNotFoundException());
       clientController.getById(15);
    }

    @Test
    public void testGetClientByOk() throws ClientNotFoundException {
          Client clientExisting = Client.builder().id(6).firstname("").surname("")
                                                .city(new City()).userType(new UserType())
                                                .DNI("").username("")
                                                .password("").isActive(true)
                                                .phoneLines(new ArrayList<>()).build();

         when(clientService.getById(6)).thenReturn(clientExisting);

         Client clientTest = clientController.getById(6).getBody();


        Assert.assertEquals(clientExisting.getUsername(), clientTest.getUsername());
        Assert.assertEquals(clientExisting.getPassword(), clientTest.getPassword());
    }

    /*@Test
    public void testGetAllOk()*/


}
