package com.utnphones.utnPhones.testUtils;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.Province;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static List<Fare> getFaresList(){
        List<Fare> list = new ArrayList<>();
        Fare fare1 = new Fare(1, new City(), new City(), (float) 10.5);
        Fare fare2 = new Fare(2, new City(), new City(), (float) 18.1);
        list.add(fare1);
        list.add(fare2);
        return list;
    }

    public static List<City> getCityList(){
        Province province = Province.builder().name("test").id(1).build();
        City city1 = City.builder().name("city1").prefix("0").id(1).province(province).build();
        City city2 = City.builder().name("city2").prefix("1").id(2).province(province).build();
        List<City> cities = new ArrayList<>();
        cities.add(city1);
        cities.add(city2);
        return cities;
    }
}
