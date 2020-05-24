package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.dao.interfaces.ClientDao;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientServiceTest {

    @Mock
    ClientDao clientDao;

    @Before
    public void setUp(){
        initMocks(this);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testClientNotFound(){
       /* Client clientExisting = Client.builder().id(14).firstname("Nombre").surname("Apellido")
                                                .city(new City()).userType(new UserType())
                                                .DNI("1500000").username("username")
                                                .password("password").isActive(true)
                                                .phoneLines(new ArrayList<>()).build();
         when(clientDao.getById(14)).thenReturn(clientExisting);

         Client clientTest = clientDao.getById(14);*/
       //when(clientDao.getById(15)).thenThrow(() -> new ClientNotFoundException("Client not found"));

    }
}
