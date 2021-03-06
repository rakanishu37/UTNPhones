package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.repository.PhoneLineRepository;
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

  /*  @Test
    public void testGetAllOk(){
        List<PhoneLine> list = TestUtils.getPhoneLines();
        when(this.phoneLineRepository.findAllByIsActive(Boolean.TRUE)).thenReturn(list);

        List<PhoneLine> listTest = this.phoneLineService.getAll();
        Assert.assertEquals(list.size(), listTest.size());
    }*/

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
        when(this.phoneLineRepository.findByLineNumberAndIsActive("231231", Boolean.TRUE)).thenReturn(Optional.ofNullable(null));
        PhoneLine phoneLineTest = this.phoneLineService.getByPhoneNumber("231231");
    }

    @Test
    public void updatePhoneLineOk() throws PhoneLineNotFoundException {
        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = LineType.builder().id(1).typeName("mobile").build();
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("city", "29205356948", lineType.getTypeName(), LineStatus.active);
        PhoneLine phoneLine = PhoneLine.builder()
                .client(new Client())
                .lineNumber(city.getPrefix() + phoneLineDTO.getLineNumber())
                .lineStatus(phoneLineDTO.getStatus())
                .lineType(lineType)
                .build();
        when(this.phoneLineRepository.findByIdAndIsActive(phoneLine.getId(), Boolean.TRUE)).thenReturn(Optional.ofNullable(phoneLine));
        when(this.phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        PhoneLine phoneLineTest = this.phoneLineService.updatePhoneLine(phoneLine.getId(), phoneLineDTO, city, lineType);

        Assert.assertEquals(phoneLine, phoneLineTest);
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void updatePhoneLineWithWrongId() throws PhoneLineNotFoundException {

        City city = new City(1, new Province(), "cityName", "223");
        LineType lineType = LineType.builder().id(1).typeName("mobile").build();
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("city", "223547748", lineType.getTypeName(), LineStatus.active);

        when(this.phoneLineRepository.findById(4)).thenReturn(Optional.ofNullable(null));
        PhoneLine phoneLineTest = this.phoneLineService.updatePhoneLine(4, phoneLineDTO, city, lineType);
    }

    @Test
    public void deleteOk() throws PhoneLineNotFoundException {
        PhoneLine phoneLine = TestUtils.getPhoneLines().get(0);
        PhoneLine deletedPhoneline = PhoneLine.builder()
                .id(phoneLine.getId())
                .client(phoneLine.getClient())
                .lineNumber(phoneLine.getLineNumber())
                .lineStatus(phoneLine.getLineStatus())
                .lineType(phoneLine.getLineType())
                .isActive(Boolean.FALSE)
                .build();
        
        when(this.phoneLineRepository.findByIdAndIsActive(phoneLine.getId(), Boolean.TRUE)).thenReturn(Optional.ofNullable(phoneLine));
        when(this.phoneLineRepository.save(deletedPhoneline)).thenReturn(deletedPhoneline);

        this.phoneLineService.delete(phoneLine.getId());
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void deleteWrongId() throws PhoneLineNotFoundException {
        when(this.phoneLineRepository.findByIdAndIsActive(5, Boolean.TRUE)).thenReturn(Optional.ofNullable(null));
        phoneLineService.delete(5);
    }
}
