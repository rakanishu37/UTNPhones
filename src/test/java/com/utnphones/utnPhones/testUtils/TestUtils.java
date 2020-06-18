package com.utnphones.utnPhones.testUtils;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.projections.CallsDates;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static List<Fare> getFaresList() {
        List<Fare> list = new ArrayList<>();
        Fare fare1 = new Fare(1, new City(), new City(), (float) 10.5);
        Fare fare2 = new Fare(2, new City(), new City(), (float) 18.1);
        list.add(fare1);
        list.add(fare2);
        return list;
    }


    public static List<City> getCityList() {
        Province province = Province.builder().name("test").id(1).build();
        City city1 = City.builder().name("city1").prefix("0").id(1).province(province).build();
        City city2 = City.builder().name("city2").prefix("1").id(2).province(province).build();
        List<City> cities = new ArrayList<>();
        cities.add(city1);
        cities.add(city2);
        return cities;
    }
        public static List<PhoneLine> getPhoneLines(){
        List<PhoneLine> list = new ArrayList<>();
        PhoneLine phoneLine1 = new PhoneLine(1, new LineType(), new Client(), "223544155", LineStatus.active, true);
        PhoneLine phoneLine2 = new PhoneLine(2, new LineType(), new Client(), "445687452", LineStatus.active, true);
        list.add(phoneLine1);
        list.add(phoneLine1);
        return list;
    }

    public static List<Call> getCalls(){
        List<Call> list = new ArrayList<>();
        Call call1 = new Call(1, getPhoneLines().get(0), getPhoneLines().get(1), new Invoice(), (float) 10.8, 150, (float) 13.7,
                new Date());
        Call call2 = new Call(1, getPhoneLines().get(0), getPhoneLines().get(1), new Invoice(), (float) 10.8, 150, (float) 13.7,
                new Date());
        list.add(call1);
        list.add(call2);
        return list;
    }

    public static List<CallsDates> getCallsDates(){
        List<CallsDates> list = new ArrayList<>();
        CallsDates callsDates;
        return list;
    }

    public static URI getCallLocation(Call call) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(call.getId())
                .toUri();
    }
}
