package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.PhoneLineService;
import com.utnphones.utnPhones.session.SessionManager;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClientControllerTest {

    ClientController clientController;

    @Mock
    private ClientService clientService;
    @Mock
    private CityService cityService;

    @Before
    public void setUp(){
        initMocks(this);
        clientController = new ClientController(clientService);
    }

    @Test
    public void testGetAllOk(){
        List<Client> list = TestUtils.getClients();
        when(this.clientService.getAll(10, 0)).thenReturn(list);

        List<Client> listTest = this.clientController.getAll(10, 0);

        Assert.assertEquals(list.size(), listTest.size());
    }

    @Test
    public void testGetId() throws ClientNotFoundException {
        Client client = TestUtils.getClients().get(0);
        when(this.clientService.getById(client.getId())).thenReturn(client);

        Client clientTest = this.clientController.getById(client.getId());

        Assert.assertEquals(client, clientTest);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testGetIdNotFound() throws ClientNotFoundException {
        when(this.clientService.getById(1)).thenThrow(new ClientNotFoundException());

        this.clientController.getById(1);
    }

    @Test
    public void createClientOk() throws CityNotFoundException, NoSuchAlgorithmException {
        City city = City.builder()
                .name("test")
                .build();

        ClientCreatedDTO dto = ClientCreatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .dni("37753328")
                .cityName(city.getName())
                .username("qwerty")
                .password("123")
                .build();

        when(cityService.getByName(city.getName())).thenReturn(city);

        Client clientCreated = Client.builder()
                .firstname(dto.getFirstname())
                .surname(dto.getSurname())
                .city(city)
                .DNI(dto.getDni())
                .username(dto.getUsername())
                .password("algo")
                .build();
        when(this.clientService.create(dto)).thenReturn(clientCreated);

        Client testClient = clientController.create(dto);

        Assert.assertEquals(clientCreated, testClient);

//        verify(clientRepository, times(1)).save(clientCreated);
    }

    @Test(expected = CityNotFoundException.class) // todo ver
    public void callCreateClientWithInvalidCityName() throws CityNotFoundException, NoSuchAlgorithmException {
        ClientCreatedDTO dto = ClientCreatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .dni("37753328")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();

        when(this.clientService.create(dto)).thenThrow(new CityNotFoundException());
        clientController.create(dto);
    }

    @Test(expected = CityNotFoundException.class)// todo ver
    public void callUpdateClientWithInvalidCityName() throws CityNotFoundException, ClientNotFoundException, NoSuchAlgorithmException {
        ClientUpdatedDTO dto = ClientUpdatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();

        Client client = Client.builder()
                .id(3)
                .firstname("test")
                .surname("test")
                .city(new City(1, new Province(), dto.getCityName(), "123"))
                .DNI("37753328")
                .username("test")
                .password("algo")
                .build();

        when(clientService.getById(3)).thenReturn(client);
        //when(cityService.getByName(dto.getCityName())).thenThrow(new CityNotFoundException());
        when(this.clientService.update(client.getId(), dto)).thenThrow(new CityNotFoundException());
        clientController.update(3, dto);
    }

    @Test(expected = ClientNotFoundException.class)
    public void callUpdateClientWithInvalidId() throws CityNotFoundException, ClientNotFoundException, NoSuchAlgorithmException {
        ClientUpdatedDTO dto = ClientUpdatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();

        when(clientService.update(3, dto)).thenThrow(new ClientNotFoundException());
        Client testClient = clientController.update(3, dto);
    }

    @Test
    public void callUpdateClientOk() throws CityNotFoundException, ClientNotFoundException, NoSuchAlgorithmException {
        City city = City.builder()
                .name("test")
                .build();

        ClientUpdatedDTO dto = ClientUpdatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();

        Client client = Client.builder()
                .id(3)
                .firstname("test")
                .surname("test")
                .city(new City())
                .DNI("37753328")
                .username("test")
                .password("algo")
                .build();

        Client updatedClient = Client.builder()
                .id(3)
                .firstname(dto.getFirstname())
                .surname(dto.getSurname())
                .city(city)
                .DNI("37753328")
                .username(dto.getUsername())
                .password("algo")
                .build();

        when(cityService.getByName(dto.getCityName())).thenReturn(city);

        //when(clientService.getById(3)).thenReturn(client);
        when(this.clientService.update(client.getId(), dto)).thenReturn(updatedClient);

        Client testClient = clientController.update(3, dto);

        Assert.assertEquals(updatedClient, testClient);
    }

    @Test(expected = ClientNotFoundException.class)
    public void callDeleteWithInvalidId() throws ClientNotFoundException, ClientIsAlreadyDeletedException {

        doThrow(new ClientNotFoundException()).when(this.clientService).delete(2);
        clientController.delete(2);
    }

    @Test
    public void callDeleteOk() throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        Integer id = 6;
        Integer check = 2;

        Client client = Client.builder()
                .id(id)
                .firstname("test")
                .surname("test")
                .city(new City())
                .DNI("37753328")
                .username("test")
                .password("algo")
                .isActive(Boolean.TRUE)
                .build();

        Client deletedClient = Client.builder()
                .id(client.getId())
                .firstname(client.getFirstname())
                .surname(client.getSurname())
                .city(client.getCity())
                .DNI(client.getDNI())
                .username(client.getUsername())
                .password(client.getPassword())
                .isActive(Boolean.FALSE)
                .build();

        when(clientService.getById(id)).thenReturn(client);

        this.clientController.delete(id);

        verify(clientService, times(1)).delete(id);
    }
}
