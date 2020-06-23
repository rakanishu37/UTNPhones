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
 // todo testGetAllNotOk
    @Test
    public void testGetAll(){
        List<Fare> list = TestUtils.getFaresList();
        when(this.fareRepository.findAll()).thenReturn(list);

        List<Fare> listTest = this.fareService.getAll();

        Assert.assertEquals(list.size(), listTest.size());
        Assert.assertEquals(list.get(0).getId(), listTest.get(0).getId());
    }

    /*@Test
    public void createTestOk(){
        Fare fare = new Fare(4, new City(), new City(), (float)10.8);
        when(this.fareRepository.save(fare)).thenReturn(fare);

        Fare fareTest = this.fareService.create(new Fare(4, new City(), new City(), (float) 10.8));
        Assert.assertEquals(fare, fareTest);
    }
*/
    @Test
    public void getByIdTestOk() throws FareNotFoundException {
        Fare fare = new Fare(4, new City(), new City(), (float)10.8);
        when(this.fareRepository.findById(fare.getId())).thenReturn(java.util.Optional.of(fare));

        Fare fareTest = this.fareService.getById(fare.getId());
        Assert.assertEquals(fare, fareTest);
    }

    @Test(expected = FareNotFoundException.class)
    public void getByIdTestNotFound() throws FareNotFoundException {
        Fare fare = null;
        when(this.fareRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(fare));
        this.fareService.getById(1);
    }

    @Test
    public void testGetFareByCitiesOk() throws CityNotFoundException {
        Fare fare = new Fare(4, new City(1, null, "City1", "112")
                , new City(2, null, "City2", "872"), (float)10.8);
        when(this.fareRepository.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId())).thenReturn(java.util.Optional.of(fare));

        Fare fareTest = this.fareService.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId());
        Assert.assertEquals(fare.getCityFrom(), fareTest.getCityFrom());
        Assert.assertEquals(fare.getCityTo(), fareTest.getCityTo());
    }

    @Test(expected = CityNotFoundException.class)
    public void testGetFareByCitiesCityNotFound() throws CityNotFoundException {
        Fare fare = new Fare(4, new City()
                , new City(2, null, "City2", "872"), (float)10.8);
        when(this.fareRepository.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId())).thenReturn(Optional.ofNullable(null));

        this.fareService.getFareByCities(fare.getCityFrom().getId(), fare.getCityTo().getId());
    }
}
