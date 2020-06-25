package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.exceptions.CallNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
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
    public void testGetAllByClientOk() throws ParseException {
        Map<String, List<CallsDates>> result = TestUtils.getCalls();
        when(this.callRepository.getAllCallByClient(1, 0, 50)).thenReturn(result.get("78910"));

        Map<String, List<CallsDates>> resultTest = this.callService.getCalls(1, null, null, 0, 50);

        Assert.assertEquals(result.get("78910").size(), resultTest.get("78910").size());
    }
    @Test
    public void testGetAllByClientAndDatesOk() throws ParseException {
        Map<String, List<CallsDates>> result = TestUtils.getCalls();
        when(this.callRepository.getAllByIdClientBetweenDates(1, "2020-05-01", "2020-05-10", 0, 50))
                .thenReturn(result.get("78910"));
        Map<String, List<CallsDates>> resultTest = this.callService.getCalls(1, "2020-05-01", "2020-05-10", 0, 50);
        Assert.assertEquals(result.get("78910").size(), resultTest.get("78910").size());
    }

    @Test(expected = ParseException.class)
    public void testGetAllByClientAndDatesWrongDates() throws ParseException {

        when(this.callRepository.getAllByIdClientBetweenDates(1, "2020-05-870", "2020-05-410", 0, 50))
                .thenReturn(null);
        this.callService.getCalls(1, "2020-05-870", "2020-05-410", 0, 50);
    }

}
