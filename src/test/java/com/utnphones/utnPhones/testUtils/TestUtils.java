package com.utnphones.utnPhones.testUtils;

import com.utnphones.utnPhones.domain.Call;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Fare;
import com.utnphones.utnPhones.domain.Invoice;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.domain.Province;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.projections.CallsDates;
import com.utnphones.utnPhones.projections.InvoiceByClient;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestUtils {

    public TestUtils() {
    }

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
        list.add(phoneLine2);
        return list;
    }

    public static Map<String, List<CallsDates>> getCalls(){
        List<CallsDates> list = new ArrayList<>();
        Map<String, List<CallsDates>> stringListMap = new HashMap<>();
       /* Call call1 = new Call(1, getPhoneLines().get(0), getPhoneLines().get(1), new Invoice(), (float) 10.8, 150, (float) 13.7,
                new Date());
        Call call2 = new Call(1, getPhoneLines().get(0), getPhoneLines().get(1), new Invoice(), (float) 10.8, 150, (float) 13.7,
                new Date());*/
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        CallsDates projection = factory.createProjection(CallsDates.class);
        CallsDates projection2 = factory.createProjection(CallsDates.class);
        projection.setCityOrigin("");
        projection.setCityDestiny("");
        projection.setPhoneNumberDestiny("123456");
        projection.setPhoneNumberOrigin("78910");
        projection.setDuration(150);
        projection.setTotalPrice( (float) 13.7);
        projection.setDate(new Date());

        projection2.setCityOrigin("");
        projection2.setCityDestiny("");
        projection2.setPhoneNumberDestiny("4455");
        projection2.setPhoneNumberOrigin("78910");
        projection2.setDuration(150);
        projection2.setTotalPrice( (float) 13.7);
        projection2.setDate(new Date());

        list.add(projection);
        list.add(projection2);
        stringListMap.put("78910", list);

        return stringListMap;
    }

    public static List<InvoiceByClient> getInvoicesByClient(){
        List<InvoiceByClient> list = new ArrayList<>();
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        InvoiceByClient projection = factory.createProjection(InvoiceByClient.class);
        InvoiceByClient projection2 = factory.createProjection(InvoiceByClient.class);

        projection.setIdInvoice(1);
        projection.setPhoneLineNumber("1234");
        projection.setNumberOfCalls(2);
        projection.setPriceCost((float) 127.4);
        projection.setInvoiceDate(new Date());
        projection.setDueDate(new Date());
        projection.setTotalPrice((float) 178.4);
        projection.setPaid(false);
        projection.setFirstName("nombre");
        projection.setLastName("apellido");

        projection2.setIdInvoice(2);
        projection2.setPhoneLineNumber("1234");
        projection2.setNumberOfCalls(2);
        projection2.setPriceCost((float) 127.4);
        projection2.setInvoiceDate(new Date());
        projection2.setDueDate(new Date());
        projection2.setTotalPrice((float) 178.4);
        projection2.setPaid(false);
        projection2.setFirstName("nombre");
        projection2.setLastName("apellido");

        list.add(projection);
        list.add(projection2);

        return list;
    }

    public static List<Invoice> getInvoices(){
        List<Invoice> list = new ArrayList<>();
        Invoice invoice1 = new Invoice(1, new PhoneLine(), 2, (float) 17.5, (float) 21.1, new Date(), new Date(), false, new ArrayList<>());
        Invoice invoice2 = new Invoice(2, new PhoneLine(), 2, (float) 17.5, (float) 21.1, new Date(), new Date(), false, new ArrayList<>());
        list.add(invoice1);
        list.add(invoice2);
        return list;
    }

    public static List<Client> getClients(){
        List<Client> list = new ArrayList<>();
        Client client1 = Client.builder().id(14).firstname("Nombre").surname("Apellido")
                .city(new City()).userType(new UserType())
                .DNI("1500000").username("username")
                .password("password").isActive(true)
                .phoneLines(new ArrayList<>()).build();
        Client client2 = Client.builder().id(14).firstname("Nombre2").surname("Apellido2")
                .city(new City()).userType(new UserType())
                .DNI("4150000").username("username2")
                .password("password2").isActive(true)
                .phoneLines(new ArrayList<>()).build();
        list.add(client1);
        list.add(client2);
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
