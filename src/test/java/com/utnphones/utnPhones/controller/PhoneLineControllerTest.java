package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.PhoneLineController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.exceptions.LineTypeNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.exceptions.PhoneLineNotIsAlreadyDeletedException;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.LineTypeService;
import com.utnphones.utnPhones.services.PhoneLineService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineControllerTest {
    @Mock
    private PhoneLineService phoneLineService;
    @Mock
    private LineTypeService lineTypeService;
    @Mock
    private ClientService clientService;
    @Mock
    private CityService cityService;

    private PhoneLineController phoneLineController;

    @Before
    public void setUp() {
        initMocks(this);
        this.phoneLineController = new PhoneLineController(phoneLineService, clientService, lineTypeService, cityService);
    }

    @Test
    public void testCreatePhoneLineOk() throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(lineType.getTypeName())
                .lineNumber("223547748")
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        Client client = TestUtils.getClients().get(0);
        PhoneLine phoneLine = PhoneLine.builder()
                .id(5)
                .client(client)
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(lineType)
                .isActive(Boolean.TRUE)
                .build();

        when(clientService.getById(client.getId())).thenReturn(client);
        when(cityService.getByName(phoneLineDTO.getCityName())).thenReturn(city);
        when(lineTypeService.findByName(phoneLineDTO.getLineType())).thenReturn(lineType);
        when(phoneLineService.create(phoneLineDTO, client, lineType, city)).thenReturn(phoneLine);

        PhoneLine phoneLineTest = phoneLineController.create(client.getId(), phoneLineDTO);
        Assert.assertEquals(phoneLine, phoneLineTest);
    }

    @Test(expected = ClientNotFoundException.class)
    public void testCreatePhoneLineThenThrowClientNotFound() throws ClientNotFoundException, CityNotFoundException, LineTypeNotFoundException {
        Integer id = 5;
        when(clientService.getById(id)).thenThrow(new ClientNotFoundException());
        PhoneLine phoneLineTest = phoneLineController.create(id, PhoneLineDTO.builder().build());
    }

    @Test(expected = CityNotFoundException.class)
    public void testCreatePhoneLineThenThrowCityNotFound() throws ClientNotFoundException, CityNotFoundException, LineTypeNotFoundException {
        Integer id = 5;
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType("test")
                .lineNumber("223547748")
                .cityName("test")
                .status(LineStatus.active)
                .build();

        Client client = TestUtils.getClients().get(0);
        when(clientService.getById(id)).thenReturn(client);
        when(cityService.getByName(phoneLineDTO.getCityName())).thenThrow(new CityNotFoundException());
        PhoneLine phoneLineTest = phoneLineController.create(id, phoneLineDTO);
    }


    @Test(expected = LineTypeNotFoundException.class)
    public void testCreatePhoneLineThenThrowLineTypeNotFound() throws ClientNotFoundException, CityNotFoundException, LineTypeNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .lineType(lineType.getTypeName())
                .lineNumber("223547748")
                .cityName(city.getName())
                .status(LineStatus.active)
                .build();
        Client client = TestUtils.getClients().get(0);

        when(clientService.getById(client.getId())).thenReturn(client);
        when(cityService.getByName(phoneLineDTO.getCityName())).thenReturn(city);
        when(lineTypeService.findByName(phoneLineDTO.getLineType())).thenThrow(new LineTypeNotFoundException());
        PhoneLine phoneLineTest = phoneLineController.create(client.getId(), phoneLineDTO);

    }


    @Test
    public void getByPhoneNumberOk() throws PhoneLineNotFoundException {
        PhoneLine phoneLine = TestUtils.getPhoneLines().get(0);
        when(this.phoneLineService.getByPhoneNumber(phoneLine.getLineNumber())).thenReturn(phoneLine);

        PhoneLine phoneLineTest = this.phoneLineController.getByPhoneNumber(phoneLine.getLineNumber());
        Assert.assertEquals(phoneLine.getId(), phoneLineTest.getId());
        Assert.assertEquals(phoneLine.getLineNumber(), phoneLineTest.getLineNumber());
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void getByPhoneNumberWrongNumber() throws PhoneLineNotFoundException {
        when(this.phoneLineService.getByPhoneNumber("231231")).thenThrow(new PhoneLineNotFoundException());
        this.phoneLineController.getByPhoneNumber("231231");
    }

    @Test
    public void updatePhoneLineOk() throws PhoneLineNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        Integer idPhoneline = 5;
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();
        Client client = TestUtils.getClients().get(0);

        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .cityName(city.getName())
                .lineNumber("223547748")
                .lineType(lineType.getTypeName())
                .status(LineStatus.active)
                .build();

        PhoneLine phoneLineUpdated = PhoneLine.builder()
                .id(idPhoneline)
                .client(client)
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(lineType)
                .isActive(Boolean.TRUE)
                .build();
        when(cityService.getByName(phoneLineDTO.getCityName())).thenReturn(city);
        when(lineTypeService.findByName(phoneLineDTO.getLineType())).thenReturn(lineType);
        when(phoneLineService.updatePhoneLine(idPhoneline, phoneLineDTO, city, lineType)).thenReturn(phoneLineUpdated);

        PhoneLine phoneLineTest = this.phoneLineController.updatePhoneLine(phoneLineUpdated.getId(), phoneLineDTO);

        Assert.assertEquals(phoneLineUpdated, phoneLineTest);
    }

    @Test(expected = CityNotFoundException.class)
    public void updatePhoneLineThenThrowCityNotFound() throws PhoneLineNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        Integer idPhoneline = 5;
        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .cityName("test")
                .lineNumber("223547748")
                .lineType("test")
                .status(LineStatus.active)
                .build();

        when(cityService.getByName(phoneLineDTO.getCityName())).thenThrow(new CityNotFoundException());

        PhoneLine phoneLineTest = this.phoneLineController.updatePhoneLine(idPhoneline, phoneLineDTO);
    }

    @Test(expected = LineTypeNotFoundException.class)
    public void updatePhoneLineThenThrowLineTypeNotFound() throws PhoneLineNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        Integer idPhoneline = 5;
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();

        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .cityName(city.getName())
                .lineNumber("223547748")
                .lineType(lineType.getTypeName())
                .status(LineStatus.active)
                .build();

        when(cityService.getByName(phoneLineDTO.getCityName())).thenReturn(city);
        when(lineTypeService.findByName(phoneLineDTO.getLineType())).thenThrow(new LineTypeNotFoundException());

        PhoneLine phoneLineTest = this.phoneLineController.updatePhoneLine(idPhoneline, phoneLineDTO);
    }
    @Test(expected = PhoneLineNotFoundException.class)
    public void updatePhoneLineThenThrowPhoneLineNotFound() throws PhoneLineNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        Integer idPhoneline = 5;
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = TestUtils.getLineType();

        PhoneLineDTO phoneLineDTO = PhoneLineDTO.builder()
                .cityName(city.getName())
                .lineNumber("223547748")
                .lineType(lineType.getTypeName())
                .status(LineStatus.active)
                .build();
        when(cityService.getByName(phoneLineDTO.getCityName())).thenReturn(city);
        when(lineTypeService.findByName(phoneLineDTO.getLineType())).thenReturn(lineType);
        when(phoneLineService.updatePhoneLine(idPhoneline, phoneLineDTO, city, lineType)).thenThrow(new PhoneLineNotFoundException());
        PhoneLine phoneLineTest = this.phoneLineController.updatePhoneLine(idPhoneline, phoneLineDTO);
    }

    @Test
    public void callDeleteOk() throws PhoneLineNotFoundException {
        Integer id = 5;
        phoneLineController.delete(id);
        verify(phoneLineService, times(1)).delete(id);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void callDeleteWithInvalidId() throws PhoneLineNotFoundException {
        Integer id = 5;
        doThrow(PhoneLineNotFoundException.class).when(phoneLineService).delete(id);
        phoneLineController.delete(id);
    }


}
