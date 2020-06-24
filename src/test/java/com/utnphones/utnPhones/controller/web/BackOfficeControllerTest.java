package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.controllers.InvoiceController;
import com.utnphones.utnPhones.controllers.PhoneLineController;
import com.utnphones.utnPhones.controllers.web.BackOfficeController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.dto.ClientCreatedDTO;
import com.utnphones.utnPhones.dto.ClientUpdatedDTO;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientIsAlreadyDeletedException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.LineTypeNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoiceByClient;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    public void testDeleteClientOk() throws ClientNotFoundException, ClientIsAlreadyDeletedException {
        doNothing().when(this.clientController).delete(1);
        ResponseEntity<?> responseTest = this.backOfficeController.deleteClient(1);

        Assert.assertEquals(HttpStatus.OK, responseTest.getStatusCode());
        verify(clientController, times(1)).delete(1);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testDeleteClientNotFound() throws ClientNotFoundException, ClientIsAlreadyDeletedException {

        doThrow(new ClientNotFoundException()).when(this.clientController).delete(1);
        this.backOfficeController.deleteClient(1);

    }

    @Test
    public void testCreatePhonelineOk() throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();

        when(UriGenerator.getLocation(phoneLine.getId())).thenReturn(URI.create("miUri.com"));

        when(this.phoneLineController.create(TestUtils.getClients().get(0).getId(), phoneLineDTO)).thenReturn(phoneLine);

        ResponseEntity<?> responseTest = this.backOfficeController.createPhoneLine(TestUtils.getClients().get(0).getId(), phoneLineDTO);
        Assert.assertEquals(URI.create("miUri.com"), responseTest.getHeaders().getLocation());
        Assert.assertEquals(HttpStatus.CREATED, responseTest.getStatusCode());
    }

    @Test(expected = ClientNotFoundException.class)
    public void testCreatePhonelineClientNotFound() throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        doThrow(new ClientNotFoundException()).when(this.phoneLineController)
                .create(TestUtils.getClients().get(0).getId(), phoneLineDTO);

        this.backOfficeController.createPhoneLine(TestUtils.getClients().get(0).getId(), phoneLineDTO);

    }

    @Test(expected = LineTypeNotFoundException.class)
    public void testCreatePhonelineLineTypeNotFound() throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        doThrow(new LineTypeNotFoundException()).when(this.phoneLineController)
                .create(TestUtils.getClients().get(0).getId(), phoneLineDTO);

        this.backOfficeController.createPhoneLine(TestUtils.getClients().get(0).getId(), phoneLineDTO);

    }

    @Test(expected = CityNotFoundException.class)
    public void testCreatePhonelineCityNotFound() throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        doThrow(new CityNotFoundException()).when(this.phoneLineController)
                .create(TestUtils.getClients().get(0).getId(), phoneLineDTO);

        this.backOfficeController.createPhoneLine(TestUtils.getClients().get(0).getId(), phoneLineDTO);

    }

    @Test
    public void testUpdatePhoneLineOk() throws LineTypeNotFoundException, PhoneLineNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();

        when(this.phoneLineController.updatePhoneLine(phoneLine.getId(), phoneLineDTO)).thenReturn(phoneLine);

        ResponseEntity<PhoneLine> responseTest = this.backOfficeController.updatePhoneLine(phoneLine.getId(), phoneLineDTO);

        Assert.assertEquals(HttpStatus.OK, responseTest.getStatusCode());
        Assert.assertEquals(phoneLine, responseTest.getBody());
    }

    @Test(expected = LineTypeNotFoundException.class)
    public void testUpdatePhoneLineLineTypeNotFound() throws LineTypeNotFoundException, PhoneLineNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        doThrow(new LineTypeNotFoundException()).when(this.phoneLineController)
                .updatePhoneLine(phoneLine.getId(), phoneLineDTO);

        this.backOfficeController.updatePhoneLine(phoneLine.getId(), phoneLineDTO);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testUpdatePhoneLinePhoneLineNotFound() throws LineTypeNotFoundException, PhoneLineNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        doThrow(new PhoneLineNotFoundException()).when(this.phoneLineController)
                .updatePhoneLine(phoneLine.getId(), phoneLineDTO);

        this.backOfficeController.updatePhoneLine(phoneLine.getId(), phoneLineDTO);
    }

    @Test(expected = CityNotFoundException.class)
    public void testUpdatePhoneLineCityNotFound() throws LineTypeNotFoundException, PhoneLineNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLine phoneLine = new PhoneLine(1, lineType, TestUtils.getClients().get(0), "2235447441",
                LineStatus.active, Boolean.TRUE);
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(phoneLine.getLineType().getTypeName())
                .lineNumber(phoneLine.getLineNumber())
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        doThrow(new CityNotFoundException()).when(this.phoneLineController)
                .updatePhoneLine(phoneLine.getId(), phoneLineDTO);

        this.backOfficeController.updatePhoneLine(phoneLine.getId(), phoneLineDTO);
    }

    @Test
    public void testPhoneLineDeleteOk() throws PhoneLineNotFoundException {
        doNothing().when(this.phoneLineController).delete(1);
        ResponseEntity<?> responseTest = this.backOfficeController.deletePhoneLine(1);

        Assert.assertEquals(HttpStatus.OK, responseTest.getStatusCode());
        verify(phoneLineController, times(1)).delete(1);
    }

    @Test(expected =PhoneLineNotFoundException.class )
    public void testPhoneLineDeletePhoneLineNotFound() throws PhoneLineNotFoundException {
        doThrow(new PhoneLineNotFoundException()).when(this.phoneLineController).delete(1);
        this.backOfficeController.deletePhoneLine(1);
    }

    @Test
    public void testGetFareByCitiesOk() throws CityNotFoundException {
        Fare fare = new Fare(1, TestUtils.getCityList().get(0), TestUtils.getCityList().get(1), (float)17.4);
        when(this.fareController.getFareByCities(fare.getCityFrom().getId(),fare.getCityTo().getId())).thenReturn(fare);

        ResponseEntity<Fare> responseTest = this.backOfficeController.getFareByCities(fare.getCityFrom().getId(),fare.getCityTo().getId());

        Assert.assertEquals(HttpStatus.OK, responseTest.getStatusCode());
        Assert.assertEquals(fare, responseTest.getBody());
    }

    @Test(expected = CityNotFoundException.class)
    public void testGetFareByCitiesCityNotFound() throws CityNotFoundException {
        doThrow(new CityNotFoundException()).when(this.fareController).getFareByCities(1,2);
        this.backOfficeController.getFareByCities(1,2);
    }

    @Test
    public void testGetInvoicesByClientOk() throws ParseException, ClientNotFoundException {
        Client client = TestUtils.getClients().get(0);
        List<InvoiceByClient> invoiceByClientList = TestUtils.getInvoicesByClient();
        when(this.invoiceController.getInvoicesByClient(client.getId(), "2020-05-05", "2020-05-07"))
                .thenReturn(invoiceByClientList);

        ResponseEntity<List<InvoiceByClient>> responseTest = this.backOfficeController
                .getInvoicesByClient(client.getId(), "2020-05-05", "2020-05-07");

        Assert.assertEquals(HttpStatus.OK, responseTest.getStatusCode());
        Assert.assertEquals(invoiceByClientList, responseTest.getBody());
    }

    @Test(expected = ParseException.class)
    public void testGetInvoicesByClientWrongDates() throws ParseException, ClientNotFoundException {
        when(this.invoiceController.getInvoicesByClient(1, "2020-05-105", "2020-05-07"))
                .thenThrow(new ParseException("", 0));

        this.backOfficeController.getInvoicesByClient(1, "2020-05-105", "2020-05-07");
    }

    @Test(expected = ClientNotFoundException.class)
    public void testGetInvoicesByClientClientNotFound() throws ParseException, ClientNotFoundException {
        doThrow(new ClientNotFoundException()).when(this.clientController).getById(1);

        this.backOfficeController.getInvoicesByClient(1, "2020-05-05", "2020-05-07");

    }
}
