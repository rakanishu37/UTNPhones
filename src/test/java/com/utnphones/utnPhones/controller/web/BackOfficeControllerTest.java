package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.PhoneLineController;
import com.utnphones.utnPhones.controllers.web.BackOfficeController;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.testUtils.TestUtils;
import com.utnphones.utnPhones.utils.UriGenerator;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@PrepareForTest(UriGenerator.class)
@RunWith(PowerMockRunner.class)
public class BackOfficeControllerTest {
    @Mock
    private PhoneLineController phoneLineController;
    @Mock
    private FareController fareController;
    @Mock
    private ClientController clientController;
    @Mock
    private InvoiceController invoiceController;
    @Mock
    private CallController callController;

    private BackOfficeController backOfficeController;

    @Before
    public void setUp(){
        initMocks(this);
        PowerMockito.mockStatic(UriGenerator.class);
        this.backOfficeController = new BackOfficeController(phoneLineController, fareController, clientController,
                invoiceController, callController);
    }

    @Test
    public void testGetAllCallsByClientOk() throws ClientNotFoundException, ParseException {
        Client client = TestUtils.getClients().get(0);
        Map<String, List<CallsDates>> calls = TestUtils.getCalls();
        when(this.clientController.getById(client.getId())).thenReturn(client);
        when(this.callController.getAllByClient(client.getId(), "", "")).thenReturn(calls);

        ResponseEntity<Map<String, List<CallsDates>>> responseTest = this.backOfficeController.getAllCallsByClient(
                client.getId(), "", ""
        );

        Assert.assertEquals(HttpStatus.OK,responseTest.getStatusCode());
        Assert.assertEquals(calls.size(), responseTest.getBody().size());
    }

    @Test
    public void testGetAllCallsByClientNoContent() throws ClientNotFoundException, ParseException {
        Client client = TestUtils.getClients().get(0);
        Map<String, List<CallsDates>> calls = new HashMap<>();
        when(this.clientController.getById(client.getId())).thenReturn(client);
        when(this.callController.getAllByClient(client.getId(), "", "")).thenReturn(calls);

        ResponseEntity<Map<String, List<CallsDates>>> responseTest = this.backOfficeController.getAllCallsByClient(
                client.getId(), "", ""
        );

        Assert.assertEquals(HttpStatus.NO_CONTENT,responseTest.getStatusCode());
    }

    @Test(expected = ClientNotFoundException.class)
    public void testGetAllCallsByClientClientNotFound() throws ClientNotFoundException, ParseException {

        when(this.clientController.getById(1)).thenThrow(new ClientNotFoundException());

        ResponseEntity<Map<String, List<CallsDates>>> responseTest = this.backOfficeController.getAllCallsByClient(
                1, "", ""
        );
    }

    @Test(expected = ParseException.class)
    public void testGetAllCallsByClientWrongDates() throws ClientNotFoundException, ParseException {

        Client client = TestUtils.getClients().get(0);
        Map<String, List<CallsDates>> calls = TestUtils.getCalls();
        when(this.clientController.getById(client.getId())).thenReturn(client);
        when(this.callController.getAllByClient(client.getId(), "2020-05-117", "2020-05-118"))
                .thenThrow(new ParseException("", 1));

        ResponseEntity<Map<String, List<CallsDates>>> responseTest = this.backOfficeController.getAllCallsByClient(
                client.getId(), "2020-05-117", "2020-05-118"
        );
    }

    @Test
    public void testGetAllClientOk(){
        List<Client> clients = TestUtils.getClients();

        when(this.clientController.getAll(10,0)).thenReturn(clients);

        ResponseEntity<List<Client>> clientsTest = this.backOfficeController.getAllClient(0, 10);

        Assert.assertEquals(HttpStatus.OK, clientsTest.getStatusCode());
        Assert.assertEquals(clients.size(), clientsTest.getBody().size());
    }

    @Test
    public void testGetAllClientNotFound(){
        List<Client> clients = new ArrayList<>();
        when(this.clientController.getAll(10,0)).thenReturn(clients);

        ResponseEntity<List<Client>> clientsTest = this.backOfficeController.getAllClient(0, 10);

        Assert.assertEquals(HttpStatus.NO_CONTENT, clientsTest.getStatusCode());
    }

    @Test
    public void testGetClientByIdOk() throws ClientNotFoundException {
        Client client = TestUtils.getClients().get(0);
        when(this.clientController.getById(client.getId())).thenReturn(client);

        ResponseEntity<Client> responseTest = this.backOfficeController.getClientById(client.getId());

        Assert.assertEquals(HttpStatus.OK, responseTest.getStatusCode());
        Assert.assertEquals(client, responseTest.getBody());
    }

    @Test(expected = ClientNotFoundException.class)
    public void testGetClientByIdClientNotFound() throws ClientNotFoundException {

        when(this.clientController.getById(1)).thenThrow(new ClientNotFoundException());

        ResponseEntity<Client> responseTest = this.backOfficeController.getClientById(1);

    }

    @Test
    public void testCreateClientOk() throws NoSuchAlgorithmException, CityNotFoundException {
        Client client = TestUtils.getClients().get(0);
        ClientCreatedDTO dto = ClientCreatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .dni("37753328")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();
        when(UriGenerator.getLocation(client.getId())).thenReturn(URI.create("miUri.com"));

        when(this.clientController.create(dto)).thenReturn(client);


        ResponseEntity<?> responseTest = this.backOfficeController.createClient(dto);
        Assert.assertEquals(URI.create("miUri.com"), responseTest.getHeaders().getLocation());
        Assert.assertEquals(HttpStatus.CREATED, responseTest.getStatusCode());

    }

    @Test(expected = CityNotFoundException.class)
    public void testCreateClientCityNotFound() throws NoSuchAlgorithmException, CityNotFoundException {
        Client client = TestUtils.getClients().get(0);
        ClientCreatedDTO dto = ClientCreatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .dni("37753328")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();

        when(this.clientController.create(dto)).thenThrow(new CityNotFoundException());

        ResponseEntity<?> responseTest = this.backOfficeController.createClient(dto);

    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateClientAlreadyExists() throws NoSuchAlgorithmException, CityNotFoundException {
        Client client = TestUtils.getClients().get(0);
        ClientCreatedDTO dto = ClientCreatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .dni("37753328")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();

        when(this.clientController.create(dto)).thenThrow(new ConstraintViolationException("", null, ""));

        ResponseEntity<?> responseTest = this.backOfficeController.createClient(dto);

    }

    @Test
    public void testUpdateClientOk() throws ClientNotFoundException, NoSuchAlgorithmException, CityNotFoundException {
        Client client = TestUtils.getClients().get(0);
        ClientUpdatedDTO dto = ClientUpdatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();
        when(this.clientController.update(client.getId(), dto)).thenReturn(client);

        ResponseEntity<Client> responseEntity = this.backOfficeController.updateClient(client.getId(), dto);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertEquals(client, responseEntity.getBody());
    }

    @Test(expected = ClientNotFoundException.class)
    public void testUpdateClientNotFound() throws ClientNotFoundException, NoSuchAlgorithmException, CityNotFoundException {

        ClientUpdatedDTO dto = ClientUpdatedDTO.builder()
                .firstname("juan")
                .surname("perez")
                .cityName("test")
                .username("qwerty")
                .password("123")
                .build();
        when(this.clientController.update(1, dto)).thenThrow(new ClientNotFoundException());

        this.backOfficeController.updateClient(1, dto);
    }
}
