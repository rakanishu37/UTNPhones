package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.web.UserController;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.TopTenDestinies;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.DestinyQuantity;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.session.SessionManager;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.when;

public class UserControllerTest {
    @Mock
    private ClientController clientController;
    @Mock
    private CallController callController;
    @Mock
    private InvoiceController invoiceController;
    @Mock
    private SessionManager sessionManager;

    private UserController userController;

    @Before
    public void setUp(){
        initMocks(this);
        this.userController = new UserController(clientController, callController, sessionManager, invoiceController);
    }

    @Test
    public void testGetInvoicesBetweenDatesOk() throws UserNotLoggedException, ParseException {
        Client client = TestUtils.getClients().get(0);
        String token = "token";
        String dateFrom = "2020-01-01";
        String dateTo = "2020-07-01";

        List<InvoiceByClient> invoices = TestUtils.getInvoicesByClient();

        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);
        when(invoiceController.getInvoicesByClient(client.getId(), dateFrom, dateTo)).thenReturn(invoices);

        ResponseEntity<List<InvoiceByClient>> responseEntity = userController.getInvoicesBetweenDates(token,dateFrom,dateTo);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(invoices, responseEntity.getBody());
    }

    @Test
    public void testGetInvoicesBetweenDatesNoContent() throws ParseException, UserNotLoggedException {
        Integer idClient = 5;
        String token = "token";
        String dateFrom = "2020-01-01";
        String dateTo = "2020-07-01";
        Client client = TestUtils.getClients().get(0);

        when(sessionManager.getCurrentUser(token)).thenReturn(client);
        when(invoiceController.getInvoicesByClient(idClient, dateFrom, dateTo)).thenReturn(Collections.emptyList());

        ResponseEntity<List<InvoiceByClient>> responseEntity = userController.getInvoicesBetweenDates(token,dateFrom,dateTo);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test(expected = ParseException.class)
    public void testGetInvoicesBetweenDatesWrongDates() throws ParseException, UserNotLoggedException {
        String dateFrom = "2020-701-01";
        String dateTo = "2020-407-01";
        Client client = TestUtils.getClients().get(0);

        when(sessionManager.getCurrentUser("token")).thenReturn(client);
        when(invoiceController.getInvoicesByClient(client.getId(), dateFrom, dateTo)).thenThrow(new ParseException("",1));

        userController.getInvoicesBetweenDates("token",dateFrom,dateTo);
    }

    @Test(expected = UserNotLoggedException.class)
    public void testGetInvoicesBetweenDatesUserNotLogged() throws ParseException, UserNotLoggedException {
        String dateFrom = "2020-701-01";
        String dateTo = "2020-407-01";
        Client client = TestUtils.getClients().get(0);

        when(sessionManager.getCurrentUser("token")).thenThrow(new UserNotLoggedException());
        userController.getInvoicesBetweenDates("token",dateFrom,dateTo);
    }


    @Test
    public void testGetCallsBetweenDatesOk() throws UserNotLoggedException, ParseException {
        Client client = TestUtils.getClients().get(0);
        String token = "token";
        Map<String, List<CallsDates>> callsBetweenDates = TestUtils.getCalls();

        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);
        when(this.callController.getAllByClient(client.getId(), "2020-05-06", "2020-06-01", 0, 50))
        .thenReturn(callsBetweenDates);

        ResponseEntity<Map<String, List<CallsDates>>> responseEntity = userController.getCallsBetweenDates
                (token,"2020-05-06","2020-06-01", 0, 50);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    public void testGetCallsBetweenDatesNoContent() throws UserNotLoggedException, ParseException {
        Client client = TestUtils.getClients().get(0);
        String token = "token";

        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);
        when(this.callController.getAllByClient(client.getId(), "2020-05-06", "2020-06-01", 0, 50))
                .thenReturn(Collections.emptyMap());

        ResponseEntity<Map<String, List<CallsDates>>> responseEntity = userController.getCallsBetweenDates(token,"2020-05-06","2020-06-01", 0, 50);

        assertEquals(HttpStatus.NO_CONTENT,responseEntity.getStatusCode());
    }

    @Test(expected = ParseException.class)
    public void testGetCallsBetweenDatesWrongDates() throws UserNotLoggedException, ParseException {
        Client client = TestUtils.getClients().get(0);
        String token = "token";

        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);
        when(this.callController.getAllByClient(client.getId(), "2020-105-06", "2020-06-01", 0, 50)).thenThrow(new ParseException("",1));

        userController.getCallsBetweenDates(token,"2020-105-06","2020-06-01",0, 50);

    }

    @Test(expected = UserNotLoggedException.class)
    public void testGetCallsBetweenDatesUserNotLogged() throws UserNotLoggedException, ParseException {

        String token = "token";

        when(this.sessionManager.getCurrentUser("token")).thenThrow(new UserNotLoggedException());

        userController.getCallsBetweenDates(token,"2020-05-06","2020-06-01", 0, 50);

    }

    @Test
    public void testGetTopTenDestiniesByClientOk() throws UserNotLoggedException {
        Client client = TestUtils.getClients().get(0);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        DestinyQuantity destinyQuantity = factory.createProjection(DestinyQuantity.class);
        destinyQuantity.setCityName("city");
        destinyQuantity.setNumberOfCalls(15);

        List<DestinyQuantity> destinyQuantityList = new ArrayList<>();
        destinyQuantityList.add(destinyQuantity);

        TopTenDestinies topTenDestinies = TopTenDestinies.builder().list(destinyQuantityList).build();

        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);
        when(this.callController.getTopTenDestiniesByClient(client.getId())).thenReturn(topTenDestinies);

        ResponseEntity<TopTenDestinies> topTenDestiniesResponseEntity = this.userController.getTopTenDestiniesByClient("token");

        Assert.assertEquals(HttpStatus.OK, topTenDestiniesResponseEntity.getStatusCode());
        Assert.assertEquals(topTenDestinies, topTenDestiniesResponseEntity.getBody());

    }

    @Test
    public void testGetTopTenDestiniesByClientNoContent() throws UserNotLoggedException {
        Client client = TestUtils.getClients().get(0);

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        DestinyQuantity destinyQuantity = factory.createProjection(DestinyQuantity.class);
        destinyQuantity.setCityName("city");
        destinyQuantity.setNumberOfCalls(15);

        List<DestinyQuantity> destinyQuantityList = new ArrayList<>();
        destinyQuantityList.add(destinyQuantity);

        TopTenDestinies topTenDestinies = TopTenDestinies.builder().list(new ArrayList<>()).build();

        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);
        when(this.callController.getTopTenDestiniesByClient(client.getId())).thenReturn(topTenDestinies);

        ResponseEntity<TopTenDestinies> topTenDestiniesResponseEntity = this.userController.getTopTenDestiniesByClient("token");

        Assert.assertEquals(HttpStatus.NO_CONTENT, topTenDestiniesResponseEntity.getStatusCode());

    }

    @Test(expected = UserNotLoggedException.class)
    public void testGetTopTenDestiniesByClientUserNotLogged() throws UserNotLoggedException {


        when(this.sessionManager.getCurrentUser("token")).thenThrow(new UserNotLoggedException());

        this.userController.getTopTenDestiniesByClient("token");

    }
}
