package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.repository.ClientRepository;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CityService cityService;

    private ClientService clientService;

    @Before
    public void setUp() {
        initMocks(this);
        this.clientService = new ClientService(clientRepository, cityService);
    }

    @Test()
    public void testGetAllOk() {
        List<Client> list = TestUtils.getClients();
        when(this.clientRepository.findAll(10, 0)).thenReturn(list);

        List<Client> listTest = this.clientService.getAll(10, 0);

        Assert.assertEquals(list.size(), listTest.size());
    }

  /*  @Test
    public void testGetId() throws ClientNotFoundException {
        Client client = TestUtils.getClients().get(0);
        when(this.clientRepository.findById(client.getId())).thenReturn(java.util.Optional.of(client));

        Client clientTest = this.clientService.getById(client.getId());

        Assert.assertEquals(client, clientTest);
    }*/

    @Test(expected = ClientNotFoundException.class)
    public void testGetIdNotFound() throws ClientNotFoundException {
        when(this.clientRepository.findById(1)).thenReturn(Optional.ofNullable(null));

        this.clientService.getById(1);
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

        Client testClient = clientService.create(dto);

        verify(clientRepository, times(1)).save(clientCreated);
    }

    @Test(expected = CityNotFoundException.class)
    public void callCreateClientWithInvalidCityName() throws CityNotFoundException, NoSuchAlgorithmException {
        ClientCreatedDTO dto = ClientCreatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .dni("37753328")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();

        when(cityService.getByName(dto.getCityName())).thenThrow(new CityNotFoundException());
        Client testClient = clientService.create(dto);
    }

    @Test(expected = CityNotFoundException.class)
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
                .city(new City())
                .DNI("37753328")
                .username("test")
                .password("algo")
                .build();

        when(clientRepository.findByIdAndIsActive(3, Boolean.TRUE)).thenReturn(Optional.ofNullable(client));
        when(cityService.getByName(dto.getCityName())).thenThrow(new CityNotFoundException());
        Client testClient = clientService.update(3, dto);
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

        when(clientRepository.findByIdAndIsActive(3, Boolean.TRUE)).thenReturn(Optional.ofNullable(null));
        Client testClient = clientService.update(3, dto);
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
        when(clientRepository.findByIdAndIsActive(3, Boolean.TRUE)).thenReturn(Optional.ofNullable(client));
        Client testClient = clientService.update(3, dto);

        verify(clientRepository, times(1)).save(updatedClient);
    }

    @Test(expected = ClientNotFoundException.class)
    public void callDeleteWithInvalidId() throws ClientNotFoundException {
        Integer id = 6;
        when(clientRepository.findByIdAndIsActive(id, Boolean.TRUE)).thenReturn(Optional.ofNullable(null));
        clientService.delete(id);
    }

    @Test
    public void callDeleteOk() throws ClientNotFoundException {
        Integer id = 6;

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

        when(clientRepository.findByIdAndIsActive(id, Boolean.TRUE)).thenReturn(Optional.ofNullable(client));
        clientService.delete(id);
        verify(clientRepository, times(1)).save(deletedClient);
    }


}
