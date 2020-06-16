package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.PhoneLineDTO;
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

    @Test // todo preguntar
    public void testCreatePhoneLineOk(){
        PhoneLineDTO phoneLineDTO = new PhoneLineDTO("city", "223547748", "mobile", LineStatus.active);
        PhoneLine phoneLine = TestUtils.getPhoneLines().get(0);
        when(this.phoneLineRepository.save(phoneLine)).thenReturn(phoneLine);

        PhoneLine phoneLine1 = this.phoneLineService.create(phoneLineDTO, phoneLine.getClient(), phoneLine.getLineType(), new City());
        System.out.println(phoneLine1);
        Assert.assertEquals(phoneLine, phoneLine1);
    }
}
