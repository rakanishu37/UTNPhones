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
    private FareRepository fareRepository;

    @Mock
    private FareService fareService;

    private FareController fareController;

    @Before
    public void setUp(){
        initMocks(this);
        this.fareController = new FareController(fareService);
    }

    @Test
    public void testGetAll(){
        List<Fare> list = TestUtils.getFaresList();
        when(this.fareService.getAll()).thenReturn(list);

        List<Fare> listTest = this.fareController.getAll();

        Assert.assertEquals(list.size(), listTest.size());
        Assert.assertEquals(list.get(0).getId(), listTest.get(0).getId());
    }

    @Test
    public void createTestOk(){
        Fare fare = new Fare(4, new City(), new City(), (float)10.8);
        when(this.fareService.create(fare)).thenReturn(fare);

        Fare fareTest = this.fareController.create(new Fare(4, new City(), new City(), (float) 10.8));
        Assert.assertEquals(fare, fareTest);
    }

    @Test
    public void getByIdTestOk() throws FareNotFoundException {
        Fare fare = new Fare(4, new City(), new City(), (float)10.8);
        when(this.fareService.getById(fare.getId())).thenReturn(fare);

        Fare fareTest = this.fareController.getById(fare.getId());
        Assert.assertEquals(fare, fareTest);
    }
 // todo preguntar
    @Test(expected = FareNotFoundException.class)
    public void getByIdTestNotFound() throws FareNotFoundException {
        Fare fare = null;
        when(this.fareService.getById(1)).thenReturn(fare);
        this.fareController.getById(1);
    }

    @Test
    public void testGetFareByCitiesOk() throws CityNotFoundException {
        Fare fare = new Fare(4, new City(1, null, "City1", "112")
                , new City(2, null, "City2", "872"), (float)10.8);
        when(this.fareService.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId())).thenReturn(fare);

        Fare fareTest = this.fareController.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId());
        Assert.assertEquals(fare.getCityFrom(), fareTest.getCityFrom());
        Assert.assertEquals(fare.getCityTo(), fareTest.getCityTo());
    }

}
