package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.repository.ClientRepository;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CityService cityService;
    @Mock
    private PhoneLineRepository phoneLineRepository;


    private ClientService clientService;

    @Before
    public void setUp(){
        initMocks(this);
        this.clientService = new ClientService(clientRepository, phoneLineRepository, cityService);
    }

    @Test()
    public void testGetAllOk(){
        List<Client> list = TestUtils.getClients();
        when(this.clientRepository.findAll(10,0)).thenReturn(list);

        List<Client> listTest = this.clientService.getAll(10,0);

        Assert.assertEquals(list.size(), listTest.size());

    }
}
