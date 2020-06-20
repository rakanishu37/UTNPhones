package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.repository.CallRepository;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.services.PhoneLineService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
@RunWith(PowerMockRunner.class)
public class CallServiceTest {
    @Mock
    private CallRepository callRepository;
    @Mock
    private PhoneLineService phoneLineService;

    private CallService callService;

    @Before
    public void setUp(){
        initMocks(this);
        this.callService = new CallService(callRepository, phoneLineService);
    }

    @Test
    public void testGetAllOk() throws ParseException {
        Map<String, List<CallsDates>> result = TestUtils.getCalls();
        when(this.callRepository.getAllCallByClient(1)).thenReturn(result.get("78910"));

        Map<String, List<CallsDates>> resultTest = this.callService.getCalls(1, null, null);

        Assert.assertEquals(result.get("78910").size(), resultTest.get("78910").size());
    }

    //@Test
    //public void testGetAllWrongDates
    //public void testGetAllWrongQuantity

  /*  @Test
    public void testCreateCallOk() throws PhoneLineNotFoundException {
        Call call = TestUtils.getCalls().get(0);

        CallDto callDto = new CallDto(call.getPhoneFrom().getLineNumber(), call.getPhoneTo().getLineNumber(), call.getDuration(), call.getDate());
        when(this.callRepository.save(call)).thenReturn(call);
        when(this.callService.getLocation(call)).thenReturn(URI.create("uri.com"));

        URI callTest = this.callService.create(callDto);

        Assert.assertEquals(call.getId(), callTest);
    }*/
/*
    @Test
    public void testGetByIdOk() throws CallNotFoundException {
        Call call = TestUtils.getCalls().get(0);
        when(this.callRepository.findById(call.getId())).thenReturn(Optional.of(call));

        Call callTest = this.callService.getById(call.getId());

        Assert.assertEquals(call, callTest);
    }
*//*
    @Test(expected = CallNotFoundException.class)
    public void testGetByIdNotFound() throws CallNotFoundException {
        when(this.callRepository.findById(1)).thenReturn(Optional.empty());
        this.callService.getById(1);

    }

    @Test
    public void testGetCallsOk(){

    }*/
}
