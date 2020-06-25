package com.utnphones.utnPhones.service;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.exceptions.CityNotFoundException;
import com.utnphones.utnPhones.repository.CityRepository;
import com.utnphones.utnPhones.services.CityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CityServiceTest {
    @Mock
    CityRepository cityRepository;

    CityService cityService;

    @Before
    public void setUp(){
        initMocks(this);
        cityService = new CityService(cityRepository);
    }

    @Test
    public void getByNameOk() throws CityNotFoundException {
        City city = City.builder().name("test").build();
        when(cityRepository.findByName("test")).thenReturn(Optional.ofNullable(city));

        City searched = cityService.getByName("test");

        Assert.assertEquals("test",searched.getName());
    }

    @Test(expected = CityNotFoundException.class)
    public void getByNameCityNotFound() throws CityNotFoundException {
        String randomName = "random";
        when(cityRepository.findByName(randomName)).thenReturn(Optional.ofNullable(null));

        cityService.getByName(randomName);
    }
}
