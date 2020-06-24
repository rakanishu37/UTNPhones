package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.PhoneLineController;
import com.utnphones.utnPhones.controllers.web.BackOfficeController;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
}
