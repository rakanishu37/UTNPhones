package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.repository.LineTypeRepository;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
import com.utnphones.utnPhones.services.LineTypeService;
import com.utnphones.utnPhones.services.PhoneLineService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PhoneLineServiceTest {

    @Mock
    private PhoneLineRepository phoneLineRepository;
    @Mock
    private LineTypeService lineTypeService;

    private PhoneLineService phoneLineService;

    @Before
    public void setUp(){
        initMocks(this);
        this.phoneLineService = new PhoneLineService(phoneLineRepository, lineTypeService);
    }

    @Test
    public void testGetAllOk(){
        List<PhoneLine> list = TestUtils.getPhoneLines();
        when(this.phoneLineRepository.findAllByIsActive(Boolean.TRUE)).thenReturn(list);

        List<PhoneLine> listTest = this.phoneLineService.getAll();
        System.out.println(listTest);
        Assert.assertEquals(list.size(), listTest.size());
    }

    @Test
    public void testCreatePhoneLineOk(){
        City city = new City(1, new Province(), "cityName", "223");
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("city", "223547748", "mobile", LineStatus.active);
        PhoneLine phoneLine = PhoneLine.builder()
                .client(new Client())
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(new LineType())
                .build();

        when(this.phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        PhoneLine phoneLineTest = this.phoneLineService.create(phoneLineDTO, phoneLine.getClient(), phoneLine.getLineType(), city);

        Assert.assertEquals(phoneLine, phoneLineTest);
    }

    @Test
    public void getByPhoneNumberOk() throws PhoneLineNotFoundException {
        PhoneLine phoneLine = TestUtils.getPhoneLines().get(0);
        when(this.phoneLineRepository.findByLineNumberAndIsActive(phoneLine.getLineNumber(), Boolean.TRUE)).thenReturn(Optional.ofNullable(phoneLine));

        PhoneLine phoneLineTest = this.phoneLineService.getByPhoneNumber(phoneLine.getLineNumber());
        Assert.assertEquals(phoneLine.getId(), phoneLineTest.getId());
        Assert.assertEquals(phoneLine.getLineNumber(), phoneLineTest.getLineNumber());
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void getByPhoneNumberWrongNumber() throws PhoneLineNotFoundException {
        PhoneLine phoneLine = null;
        when(this.phoneLineRepository.findByLineNumberAndIsActive("231231", Boolean.TRUE)).thenReturn(Optional.ofNullable(null));

        PhoneLine phoneLineTest = this.phoneLineService.getByPhoneNumber("231231");

    }

    @Test
    public void updatePhoneLineOk() throws PhoneLineNotFoundException {

        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = LineType.builder().id(1).typeName("mobile").build();
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("city", "223547748", lineType.getTypeName(), LineStatus.active);
        PhoneLine phoneLine = PhoneLine.builder()
                .client(new Client())
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(lineType)
                .build();
        when(this.phoneLineRepository.findById(phoneLine.getId())).thenReturn(Optional.ofNullable(phoneLine));
        when(this.phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        PhoneLine phoneLineTest = this.phoneLineService.updatePhoneLine(phoneLine.getId(), phoneLineDTO, city, lineType);

        Assert.assertEquals(phoneLine, phoneLineTest);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void updatePhoneLineWrongNumber() throws PhoneLineNotFoundException {

        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = LineType.builder().id(1).typeName("mobile").build();
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("city", "223547748", lineType.getTypeName(), LineStatus.active);
        PhoneLine phoneLine = PhoneLine.builder()
                .client(new Client())
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(lineType)
                .build();
        when(this.phoneLineRepository.findById(4)).thenReturn(Optional.ofNullable(null));
        when(this.phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        PhoneLine phoneLineTest = this.phoneLineService.updatePhoneLine(4, phoneLineDTO, city, lineType);

        Assert.assertEquals(phoneLine, phoneLineTest);
    }

    @Test// todo preguntar
    public void deleteOk() throws PhoneLineNotFoundException {
        PhoneLine phoneLine = TestUtils.getPhoneLines().get(0);
       doNothing().when(this.phoneLineRepository.findByIdAndIsActive(phoneLine.getId(), Boolean.TRUE));
        when(this.phoneLineService.getById(phoneLine.getId())).thenReturn(phoneLine);
        when(this.phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        this.phoneLineService.delete(phoneLine.getId());
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void deleteWrongNumber() throws PhoneLineNotFoundException {
        PhoneLine phoneLine = TestUtils.getPhoneLines().get(0);
        when(this.phoneLineService.getById(phoneLine.getId())).thenReturn(phoneLine);
        when(this.phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        this.phoneLineService.delete(phoneLine.getId());
    }
}
