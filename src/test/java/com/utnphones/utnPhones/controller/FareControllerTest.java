package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.FareNotFoundException;
import com.utnphones.utnPhones.repository.FareRepository;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.FareService;
import com.utnphones.utnPhones.testUtils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FareControllerTest {

    @Mock
    private FareService fareService;
    @Mock
    private CityService cityService;

    private FareController fareController;

    @Before
    public void setUp() {
        initMocks(this);
        this.fareController = new FareController(fareService, cityService);
    }

    @Test
    public void testGetFareByCitiesOk() throws CityNotFoundException, FareNotFoundException {

        City cityFrom = new City(2, null, "City1", "872");
        City cityTo = new City(2, null, "City2", "872");
        Fare fare = new Fare(4, cityFrom, cityTo, 18.4f);

        when(this.fareService.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId())).thenReturn(fare);
        when(this.cityService.getByName(cityFrom.getName())).thenReturn(cityFrom);
        when(this.cityService.getByName(cityTo.getName())).thenReturn(cityTo);

        Fare fareTest = this.fareController.getFareByCities(fare.getCityFrom().getName(), fare.getCityTo().getName());
        Assert.assertEquals(fare.getCityFrom(), fareTest.getCityFrom());
        Assert.assertEquals(fare.getCityTo(), fareTest.getCityTo());
    }

    @Test(expected = CityNotFoundException.class)
    public void testGetFareByCitiesThenThrowCityNotFound() throws CityNotFoundException, FareNotFoundException {

        when(this.cityService.getByName("name")).thenThrow(new CityNotFoundException());

        fareController.getFareByCities("name", "name2");
    }


}
