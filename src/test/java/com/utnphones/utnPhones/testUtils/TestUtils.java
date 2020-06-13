package com.utnphones.utnPhones.testUtils;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Fare;

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
}
