package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Fare;
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

public class FareServiceTest {
    @Mock
    FareRepository fareRepository;


    FareService fareService;

    @Before
    public void setUp(){
        initMocks(this);
        this.fareService = new FareService(fareRepository);
    }

    @Test
    public void testGetAll(){
        List<Fare> list = TestUtils.getFaresList();
        when(this.fareRepository.findAll()).thenReturn(list);

        List<Fare> listTest = this.fareService.getAll();

        Assert.assertEquals(list.size(), listTest.size());
        Assert.assertEquals(list.get(0).getId(), listTest.get(0).getId());
    }

    @Test
    public void createTest(){
        Fare fare = new Fare(4, new City(), new City(), (float)10.8);
        when(this.fareRepository.save(fare)).thenReturn(fare);

        Fare fareTest = this.fareService.create(new Fare(4, new City(), new City(), (float) 10.8));
        Assert.assertEquals(fare, fareTest);
    }

    @Test
    public void getByIdTest() throws FareNotFoundException {
        Fare fare = new Fare(4, new City(), new City(), (float)10.8);
        when(this.fareRepository.findById(fare.getId())).thenReturn(java.util.Optional.of(fare));

        Fare fareTest = this.fareService.getById(fare.getId());
        Assert.assertEquals(fare, fareTest);
    }

    @Test(expected = FareNotFoundException.class)
    public void getByIdTestNotFound() throws FareNotFoundException {
        //Fare fare = new Fare(987, new City(), new City(), (float)10.8);
        when(this.fareRepository.findById(1)).thenReturn(null);
        this.fareService.getById(1);
    }
}
