package com.utnphones.utnPhones.controller.web;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.controllers.web.AntennaController;
import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.testUtils.TestUtils;
import com.utnphones.utnPhones.utils.UriGenerator;
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
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@PrepareForTest(UriGenerator.class)
@RunWith(PowerMockRunner.class)
public class AntennaControllerTest {
    @Mock
    private CallController callController;

    private AntennaController antennaController;

    @Before
    public void setUp(){
        initMocks(this);
        PowerMockito.mockStatic(UriGenerator.class);
        antennaController = new AntennaController(callController);
    }

    @Test
    public void testCreateWithValidDto() throws PhoneLineNotFoundException {
        PhoneLine from = TestUtils.getPhoneLines().get(0);
        PhoneLine to = TestUtils.getPhoneLines().get(1);
        Date date = new Date();
        CallDto callDto = CallDto.builder()
                .numberFrom(from.getLineNumber())
                .numberTo(to.getLineNumber())
                .duration(100)
                .date(date)
                .build();

        Call call = Call.builder()
                .id(5)
                .phoneFrom(from)
                .phoneTo(to)
                .invoice(null)
                .duration(callDto.getDuration())
                .date(callDto.getDate())
                .fare(1f)
                .build();

        when(callController.create(callDto)).thenReturn(call);
        when(UriGenerator.getLocation(call.getId())).thenReturn(URI.create(String.valueOf(call.getId())));
        ResponseEntity response = antennaController.create(callDto);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assert.assertEquals(URI.create(String.valueOf(call.getId())), response.getHeaders().getLocation());

    }

    @Test
    public void testCreateWithInvalidPhoneline() throws PhoneLineNotFoundException {
        PhoneLine from = TestUtils.getPhoneLines().get(0);
        PhoneLine to = TestUtils.getPhoneLines().get(1);
        Date date = new Date();
        CallDto callDto = CallDto.builder()
                .numberFrom(from.getLineNumber())
                .numberTo(to.getLineNumber())
                .duration(100)
                .date(date)
                .build();

        when(callController.create(callDto)).thenThrow(new PhoneLineNotFoundException());
        ResponseEntity response = antennaController.create(callDto);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


}
