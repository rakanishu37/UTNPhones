package com.utnphones.utnPhones.testUtils;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Fare;
<<<<<<< HEAD
import com.utnphones.utnPhones.domain.Province;
=======
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
>>>>>>> 1c75c9f7aa37e82bb521a3f7f9e4d4a347f16482

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

<<<<<<< HEAD
    public static List<City> getCityList(){
        Province province = Province.builder().name("test").id(1).build();
        City city1 = City.builder().name("city1").prefix("0").id(1).province(province).build();
        City city2 = City.builder().name("city2").prefix("1").id(2).province(province).build();
        List<City> cities = new ArrayList<>();
        cities.add(city1);
        cities.add(city2);
        return cities;
=======
    public static List<PhoneLine> getPhoneLines(){
        List<PhoneLine> list = new ArrayList<>();
        PhoneLine phoneLine1 = new PhoneLine(1, new LineType(), new Client(), "223544155", LineStatus.active, true);
        PhoneLine phoneLine2 = new PhoneLine(2, new LineType(), new Client(), "445687452", LineStatus.active, true);
        list.add(phoneLine1);
        list.add(phoneLine1);
        return list;
>>>>>>> 1c75c9f7aa37e82bb521a3f7f9e4d4a347f16482
    }
}
