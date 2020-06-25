package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.FareController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.exceptions.FareNotFoundException;
import com.utnphones.utnPhones.repository.FareRepository;
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

    private FareController fareController;

    @Before
    public void setUp() {
        initMocks(this);
        this.fareController = new FareController(fareService);
    }

    @Test
    public void testGetFareByCitiesOk() throws CityNotFoundException {
        Fare fare = new Fare(4, new City(1, null, "City1", "112")
                , new City(2, null, "City2", "872"), (float) 10.8);
        when(this.fareService.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId())).thenReturn(fare);

        Fare fareTest = this.fareController.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId());
        Assert.assertEquals(fare.getCityFrom(), fareTest.getCityFrom());
        Assert.assertEquals(fare.getCityTo(), fareTest.getCityTo());
    }

    @Test(expected = CityNotFoundException.class)
    public void testGetFareByCitiesThenThrowCityNotFound() throws CityNotFoundException {
        Fare fare = new Fare(4, new City(1, null, "City1", "112")
                , new City(2, null, "City2", "872"), (float) 10.8);
        when(this.fareService.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId()))
                .thenThrow(new CityNotFoundException());

        fareController.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId());
    }


}
