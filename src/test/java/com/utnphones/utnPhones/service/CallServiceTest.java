package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.projections.PersonDuration;
import com.utnphones.utnPhones.repository.CallRepository;
import com.utnphones.utnPhones.services.CallService;
import com.utnphones.utnPhones.services.PhoneLineService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallServiceTest {
    @Mock
    private CallRepository callRepository;
    @Mock
    private PhoneLineService phoneLineService;


    private CallService callService;

    @Before
    public void setUp() {
        initMocks(this);
        callService = new CallService(callRepository, phoneLineService);
    }


    @Test
    public void testGetDurationInMonth() throws ParseException {
        String yearMonth = "2020-05";
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM");
        Date date = sd.parse(yearMonth);
        String stringDate = sd.format(date);
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        PersonDuration projection = factory.createProjection(PersonDuration.class);

        projection.setName("juan");
        projection.setLastname("perez");
        projection.setTotalDuration(5000);
        List<PersonDuration> list = new ArrayList<>();
        list.add(projection);

        when(callRepository.getDurationInMonth(stringDate)).thenReturn(list);

        List<PersonDuration> test = callService.getDurationInMonth(yearMonth);
        assertEquals(list.size(),test.size());
    }


}
