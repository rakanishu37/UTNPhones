package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.web.UserController;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.dto.TotalPriceDTO;
import com.utnphones.utnPhones.exceptions.UserNotLoggedException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.TotalPrice;
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
        this.userController = new UserController(clientController, callController, sessionManager, invoiceController);
    }

    @Test
    public void testGetTotalPriceOk() throws UserNotLoggedException {
        Client client = TestUtils.getClients().get(0);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        TotalPrice projection = factory.createProjection(TotalPrice.class);
        projection.setFirstname(client.getFirstname());
        projection.setSurname(client.getSurname());
        projection.setTotalPrice((float) 170.8);

        when(this.clientController.getTotalPrice(client.getId())).thenReturn(projection);
        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);

        ResponseEntity<TotalPrice> totalPriceTest = this.userController.getTotalPrice("token");

        Assert.assertEquals(HttpStatus.OK, totalPriceTest.getStatusCode());
        Assert.assertEquals(projection, totalPriceTest.getBody());
    }

    @Test(expected = UserNotLoggedException.class)
    public void testGetTotalPriceUserNotLogged() throws UserNotLoggedException {


        when(this.sessionManager.getCurrentUser("token")).thenThrow(new UserNotLoggedException());
        this.userController.getTotalPrice("token");

    }

    @Test
    public void testGetTotalPriceNoContent() throws UserNotLoggedException {
        Client client = TestUtils.getClients().get(0);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        TotalPrice projection = factory.createProjection(TotalPrice.class);

        when(this.clientController.getTotalPrice(client.getId())).thenReturn(projection);
        when(this.sessionManager.getCurrentUser("token")).thenReturn(client);

        ResponseEntity<TotalPrice> totalPriceTest = this.userController.getTotalPrice("token");

        Assert.assertEquals(HttpStatus.NO_CONTENT, totalPriceTest.getStatusCode());

    }
}
