package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.web.UserController;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import com.utnphones.utnPhones.session.SessionManager;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

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
        userController = new UserController(clientController,callController,sessionManager,invoiceController);
    }

    @Test
    public void testGetInvoicesBetweenDates() throws UserNotLoggedException, ParseException {
        Integer idClient = 5;
        String token = "token";
        String dateFrom = "2020/01/01";
        String dateTo = "2020/07/01";
        List<InvoiceByClient> invoices = TestUtils.getInvoicesByClient();
        TestUtils.getClients().get(0);
        doReturn(idClient).when(sessionManager).getCurrentUser(token).getId();
        when(invoiceController.getInvoicesByClient(idClient, dateFrom, dateTo)).thenReturn(invoices);

        ResponseEntity responseEntity = userController.getInvoicesBetweenDates(token,dateFrom,dateTo);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}
