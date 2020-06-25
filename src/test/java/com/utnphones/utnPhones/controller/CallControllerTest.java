package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.dto.CallDto;
import com.utnphones.utnPhones.dto.TopTenDestinies;
import com.utnphones.utnPhones.exceptions.PhoneLineNotFoundException;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.DestinyQuantity;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {
    @Mock
    private CallService callService;

    private CallController callController;

    @Before
    public void setUp() {
        initMocks(this);
        this.callController = new CallController(callService);
    }

    @Test
    public void testCreate() throws PhoneLineNotFoundException {
        List<PhoneLine> phoneLines = TestUtils.getPhoneLines();
        Date date = new Date();
        CallDto callDto = CallDto.builder()
                .numberFrom(phoneLines.get(0).getLineNumber())
                .numberTo(phoneLines.get(1).getLineNumber())
                .date(date)
                .duration(100)
                .build();

        Call call = Call.builder()
                .phoneFrom(phoneLines.get(0))
                .phoneTo(phoneLines.get(1))
                .date(date)
                .fare(5000f)
                .duration(100)
                .invoice(null)
                .build();

        when(callService.create(callDto)).thenReturn(call);
        Call test = callController.create(callDto);
        assertEquals(callDto.getNumberFrom(),call.getPhoneFrom().getLineNumber());
        assertEquals(callDto.getNumberTo(),call.getPhoneTo().getLineNumber());
        assertEquals(callDto.getDate(),call.getDate());
        assertEquals(callDto.getDuration(),call.getDuration());
    }

    @Test(expected = PhoneLineNotFoundException.class)
    public void testCreateWithInvalidPhoneNumber() throws PhoneLineNotFoundException {
        Date date = new Date();
        CallDto callDto = CallDto.builder()
                .numberFrom("4846")
                .numberTo("1325")
                .date(date)
                .duration(100)
                .build();
        when(callService.create(callDto)).thenThrow(new PhoneLineNotFoundException());
        callController.create(callDto);
    }


    @Test
    public void testGetAllByClientOk() throws ParseException {
        Map<String, List<CallsDates>> result = TestUtils.getCalls();
        when(this.callService.getCalls(1, null, null, 0, 50)).thenReturn(result);

        Map<String, List<CallsDates>> resultTest = this.callController.getAllByClient(1, null, null, 0, 50);

        assertEquals(result, resultTest);
        assertEquals(result.size(), resultTest.size());
    }

    @Test
    public void testGetAllByClientAndDatesOk() throws ParseException {
        Map<String, List<CallsDates>> result = TestUtils.getCalls();
        when(this.callService.getCalls(1, "2020-05-01", "2020-05-10", 0, 50))
                .thenReturn(result);
        Map<String, List<CallsDates>> resultTest = this.callController.getAllByClient(1, "2020-05-01", "2020-05-10", 0, 50);
        assertEquals(result, resultTest);
    }

    @Test
    public void testGetTopTenDestiniesByClientOk() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        DestinyQuantity projection = factory.createProjection(DestinyQuantity.class);

        projection.setCityName("test");
        projection.setNumberOfCalls(5);
        List<DestinyQuantity> list = new ArrayList<>();
        list.add(projection);

        TopTenDestinies topTenDestinies = TopTenDestinies.builder()
                .list(list)
                .build();

        Integer idClient = 3;
        when(callService.getTopTenDestiniesByClient(idClient)).thenReturn(topTenDestinies);

        callController.getTopTenDestiniesByClient(idClient);
        verify(callService,times(1)).getTopTenDestiniesByClient(idClient);
    }
}
