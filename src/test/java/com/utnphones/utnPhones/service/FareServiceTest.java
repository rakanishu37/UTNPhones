package com.utnphones.utnPhones.service;

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
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FareServiceTest {
    @Mock
    private FareRepository fareRepository;

    private FareService fareService;

    @Before
    public void setUp(){
        initMocks(this);
        this.fareService = new FareService(fareRepository);
    }

    @Test
    public void testGetFareByCitiesOk() throws FareNotFoundException {
        Fare fare = new Fare(4, new City(1, null, "City1", "112")
                , new City(2, null, "City2", "872"), (float)10.8);
        when(this.fareRepository.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId())).thenReturn(java.util.Optional.of(fare));

        Fare fareTest = this.fareService.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId());
        Assert.assertEquals(fare.getCityFrom(), fareTest.getCityFrom());
        Assert.assertEquals(fare.getCityTo(), fareTest.getCityTo());
    }

}
