package com.utnphones.utnPhones.controller;

import com.utnphones.utnPhones.controllers.CityController;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.repository.CityRepository;
import com.utnphones.utnPhones.services.CityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityControllerTest {
    @Mock
    private CityService cityService;

    private CityController cityController;

    @Before
    public void setUp(){
        initMocks(this);
        this.cityController = new CityController(cityService);
    }

    @Test
    public void getByNameOk() throws CityNotFoundException {
        City city = City.builder().name("test").build();
        when(cityService.getByName("test")).thenReturn(city);

        City searched = cityController.getByName("test");

        Assert.assertEquals("test",searched.getName());
    }

    @Test(expected = CityNotFoundException.class)
    public void getByNameCityNotFound() throws CityNotFoundException {
        String randomName = "random";
        when(cityController.getByName(randomName)).thenThrow(new CityNotFoundException());

        this.cityController.getByName(randomName);
    }
}
