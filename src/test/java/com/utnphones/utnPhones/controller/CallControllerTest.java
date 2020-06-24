package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.CallController;
import com.utnphones.utnPhones.exceptions.ClientNotFoundException;
import com.utnphones.utnPhones.projections.PersonDuration;
import com.utnphones.utnPhones.services.CallService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CallControllerTest {
    CallController callController;

    @Mock
    CallService callService;

    @Before
    public void setUp(){
        initMocks(this);
        callController = new CallController(callService);
    }

    @Test(expected = ParseException.class)
    public void testGetDurationInMonthWrongFormat() throws ParseException {
        String yearMonth = "01-5855";
        when(callService.getDurationInMonth(yearMonth)).thenThrow(new ParseException("Formato erroneo",1));
        callController.getPersonDurationInMonth(yearMonth);
    }

    @Test
    public void testGetDurationInMonthNoContent() throws ParseException {
        String yearMonth = "2020-05";
        when(callService.getDurationInMonth(yearMonth)).thenReturn(Collections.emptyList());
        ResponseEntity<List<PersonDuration>> response = callController.getPersonDurationInMonth(yearMonth);

        Assert.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void testGetDurationInMonthOk() throws ParseException {
        String yearMonth = "2020-05";

        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        PersonDuration projection = factory.createProjection(PersonDuration.class);

        projection.setName("juan");
        projection.setLastname("perez");
        projection.setTotalDuration(5000);
        List<PersonDuration> list = new ArrayList<>();
        list.add(projection);

        when(callService.getDurationInMonth(yearMonth)).thenReturn(list);
        ResponseEntity<List<PersonDuration>> response = callController.getPersonDurationInMonth(yearMonth);

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertEquals(list.size(),response.getBody().size());

    }


}
