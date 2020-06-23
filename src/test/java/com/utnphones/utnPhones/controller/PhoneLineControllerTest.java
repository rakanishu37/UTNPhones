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
    public void setUp(){
        initMocks(this);
        this.phoneLineController = new PhoneLineController(phoneLineService, clientService, lineTypeService, cityService);
    }

  /*  @Test // todo ver
    public void testCreatePhoneLineOk() throws ClientNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("cityName", "223547748", "home", LineStatus.active);
        PhoneLine phoneLine = PhoneLine.builder()
                .client(TestUtils.getClients().get(0))
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(TestUtils.getLineType())
                .build();

        when(this.phoneLineService.create(phoneLineDTO, TestUtils.getClients().get(0), TestUtils.getLineType(), city))
                .thenReturn(phoneLine);

        PhoneLine phoneLineTest = this.phoneLineController.create(TestUtils.getClients().get(0).getId(),phoneLineDTO);
        Assert.assertEquals(phoneLine, phoneLineTest);
    }*/

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

    /*@Test // todo ver
    public void updatePhoneLineOk() throws PhoneLineNotFoundException, LineTypeNotFoundException, CityNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = LineType.builder().id(1).typeName("mobile").build();
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("city", "223547748", lineType.getTypeName(), LineStatus.active);
        PhoneLine phoneLine = PhoneLine.builder()
                .client(TestUtils.getClients().get(0))
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(lineType)
                .build();
        when(this.phoneLineService.getById(phoneLine.getId())).thenReturn(phoneLine);
        when(this.phoneLineService.create(phoneLineDTO, TestUtils.getClients().get(0), lineType, city)).thenReturn(phoneLine);

        PhoneLine phoneLineTest = this.phoneLineController.updatePhoneLine(phoneLine.getId(), phoneLineDTO);

        Assert.assertEquals(phoneLine, phoneLineTest);
    }*/
}
