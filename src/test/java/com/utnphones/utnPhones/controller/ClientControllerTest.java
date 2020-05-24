package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.dao.interfaces.ClientDao;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.service.ClientServiceTest;
import com.utnphones.utnPhones.services.ClientService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientControllerTest {

    @Mock
    ClientDao clientDao;

    @Mock
    ClientService clientService;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testClientNotFound() throws ClientNotFoundException {
       when(clientService.getById(15)).thenThrow(new ClientNotFoundException("Client not found"));
       clientService.getById(15);
    }

    @Test
    public void testClientFound() throws ClientNotFoundException {
          Client clientExisting = Client.builder().id(6).firstname("").surname("")
                                                .city(new City()).userType(new UserType())
                                                .DNI("").username("")
                                                .password("").isActive(true)
                                                .phoneLines(new ArrayList<>()).build();

         when(clientService.getById(6)).thenReturn(clientExisting);

         Client clientTest = clientService.getById(6);

        Assert.assertEquals(clientExisting.getUsername(), clientTest.getUsername());
        Assert.assertEquals(clientExisting.getPassword(), clientTest.getPassword());
    }
}
