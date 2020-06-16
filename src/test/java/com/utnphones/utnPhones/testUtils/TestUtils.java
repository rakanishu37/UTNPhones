package com.utnphones.utnPhones.testUtils;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;

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

    public static List<PhoneLine> getPhoneLines(){
        List<PhoneLine> list = new ArrayList<>();
        PhoneLine phoneLine1 = new PhoneLine(1, new LineType(), new Client(), "223544155", LineStatus.active, true);
        PhoneLine phoneLine2 = new PhoneLine(2, new LineType(), new Client(), "445687452", LineStatus.active, true);
        list.add(phoneLine1);
        list.add(phoneLine1);
        return list;
    }
}
