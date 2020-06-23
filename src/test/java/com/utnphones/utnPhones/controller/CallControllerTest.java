package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {
    @Mock
    private CallService callService;

    private CallController callController;

    @Before
    public void setUp(){
        initMocks(this);
        this.callController = new CallController(callService);
    }

    @Test
    public void testGetAllByClientOk() throws ParseException {
        Map<String, List<CallsDates>> result = TestUtils.getCalls();
        when(this.callService.getCalls(1, null, null)).thenReturn(result);

        Map<String, List<CallsDates>> resultTest = this.callController.getAllByClient(1, null, null);

        Assert.assertEquals(result, resultTest);
    }

    @Test
    public void testGetAllByClientAndDatesOk() throws ParseException {
        Map<String, List<CallsDates>> result = TestUtils.getCalls();
        when(this.callService.getCalls(1, "2020-05-01", "2020-05-10"))
                .thenReturn(result);
        Map<String, List<CallsDates>> resultTest = this.callController.getAllByClient(1, "2020-05-01", "2020-05-10");
        Assert.assertEquals(result, resultTest);
    }
}
