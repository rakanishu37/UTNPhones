package com.utnphones.utnPhones.controllers;

import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.LineStatus;
import com.utnphones.utnPhones.domain.LineType;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.domain.UserType;
import com.utnphones.utnPhones.repository.CityRepository;
import com.utnphones.utnPhones.repository.LineTypeRepository;
import com.utnphones.utnPhones.repository.UserTypeRepository;
import com.utnphones.utnPhones.services.CityService;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.PhoneLineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


//@RestController
//@RequestMapping("/generate")
public class RandomGeneration {

    private CityRepository cityRepository;
    private CityService cityService;
    private UserTypeRepository userTypeRepository;
    private LineTypeRepository lineTypeRepository;
    private PhoneLineService phoneLineService;
    private List<String> firstnames;
    private List<String> surnames;
    private List<PhoneLine> phoneLines;
    private ClientService clientService;
    //private UserTypeService userTypeService;
    private List<City> cities;
    private List<String> fullNumbers;
    private List<String> DNI;
    @Autowired
    public RandomGeneration(CityRepository cityRepository, CityService cityService, UserTypeRepository userTypeRepository,
                            ClientService clientService, PhoneLineService phoneLineService,
                            LineTypeRepository lineTypeRepository/*, UserTypeService userTypeService*/) {
        this.cityRepository = cityRepository;
        this.cityService = cityService;
        this.userTypeRepository = userTypeRepository;
        this.clientService = clientService;
        this.phoneLineService = phoneLineService;
        this.lineTypeRepository = lineTypeRepository;
        this.fullNumbers = new ArrayList<>();
       // this.userTypeService = userTypeService;
        this.fillData();
    }

    @PostMapping("/clients/")
    public List<Client> insertPerson(){

        List<Client> clients = new ArrayList<>();
        UserType userType = new UserType(1, "client");
      //  System.out.println(this.userTypeService.create(userType));
       // Person person = new Person(cities.get((Integer)(Math.random() * cities.size()) + 0)),)
        for (int i=0; i<10000;i++){
        String firstname = this.firstnames.get((int) (Math.random() * this.firstnames.size()));
        Client client = Client.builder()
                .city(this.cityService.getAll().get((int)(Math.random() * cities.size()) + 0))
                .firstname(firstname)
                .surname(this.firstnames.get((int) (Math.random() * this.surnames.size())))
                .DNI(this.DNI.get(i))
                .username(firstname + String.valueOf((int) (Math.random() * 40000000)) + "@gmail.com")
                .password("123456")
                .userType(this.userTypeRepository.findById(1).get())
                .phoneLines(new ArrayList<>())
                .build();
        clients.add(this.clientService.create(client));
        }
        return clients;
    }

    @PostMapping("/phoneLines/")
    public List<PhoneLine> insertPhoneLines(){
        this.phoneLines = new ArrayList<>();
        Set<String> numbers = new HashSet<String>();
        do{
            String random = String.valueOf( (int) (Math.random() * 1999999) + 4000000);
            numbers.add(random);
        }while(numbers.size()<10000);

        List<String> prefixes = cities.stream().map(City::getPrefix).collect(Collectors.toList());


        for (int i=0; i<4;i++) {
            Client client = this.clientService.getById(i+1).get();
            Client cliente = Client.builder()
                    .id(client.getId())
                    .city(client.getCity())
                    .firstname(client.getFirstname())
                    .surname(client.getSurname())
                    .DNI(client.getDNI())
                    .username(client.getUsername())
                    .password(client.getPassword())
                    .userType(client.getUserType())
                    .phoneLines(client.getPhoneLines())
                    .build();
            PhoneLine phoneLine = PhoneLine.builder()
                           .lineType(this.lineTypeRepository.findById(1).get())
                           .client( cliente)
                           .number(this.fullNumbers.get(i))
                           .lineStatus(LineStatus.active)
                           .build();

            phoneLines.add(this.phoneLineService.create(phoneLine));
        }
        return phoneLines;
    }

    public void fillData(){
        this.firstnames = new ArrayList<>();
        this.surnames = new ArrayList<>();
        this.DNI = new ArrayList<>();
        Set<String> dnis = new HashSet<>();
        this.cities = cityRepository.findAll();

        UserType userType = new UserType(1, "client");
        //System.out.println(this.userTypeService.create(userType));

        Set<String> numbers = new HashSet<String>();
        do{
            String random = String.valueOf( (int) (Math.random() * 1999999) + 4000000);
            numbers.add(random);
        }while(numbers.size()<10000);

        //numbers.forEach(n -> System.out.println(n));

        List<String> prefixes = cities.stream().map(city -> city.getPrefix()).collect(Collectors.toList());
       // prefixes.forEach(p -> System.out.println(p));

       Iterator<String> it = numbers.iterator();

        /*while(it.hasNext()){
            fullNumbers.add(it.next());
        }*/

        for(int i=0;i<10000;i++){
            fullNumbers.add(prefixes.get((int) (Math.random() * 26) + 0) + it.next());
        }
        /*fullNumbers.forEach(f ->{
            System.out.println(f);
        });
        System.out.println(fullNumbers.size());
*/
        this.firstnames.add("Ottavia");
        this.firstnames.add("Joël");
        this.firstnames.add("Fred");
        this.firstnames.add("Diana");
        this.firstnames.add("Hendrik");
        this.firstnames.add("Lenox");
        this.firstnames.add("Blagoj");
        this.firstnames.add("Nike");
        this.firstnames.add("Sherley");
        this.firstnames.add("Achille");
        this.firstnames.add("Iantha");
        this.firstnames.add("Victorine");
        this.firstnames.add("Sekai");
        this.firstnames.add("Nagi");
        this.firstnames.add("Nurten");
        this.firstnames.add("Camilla");
        this.firstnames.add("Koralia");
        this.firstnames.add("Cahal");
        this.firstnames.add("Punit");
        this.firstnames.add("Joakim");
        this.firstnames.add("Nestor");
        this.firstnames.add("Vlas");
        this.firstnames.add("Anton");
        this.firstnames.add("Sofiya");
        this.firstnames.add("Asya");
        this.firstnames.add("Germund");
        this.firstnames.add("Renat");
        this.firstnames.add("Tikhon");
        this.firstnames.add("Othmar");
        this.firstnames.add("Lidiya");
        this.firstnames.add("Varya");
        this.firstnames.add("Dmitri");
        this.firstnames.add("Mila");
        this.firstnames.add("Innokenty");
        this.firstnames.add("Veniamin");



        this.surnames.add("Stewart");
        this.surnames.add("Harrison");
        this.surnames.add("Harmon");
        this.surnames.add("Oliver");
        this.surnames.add("Glover");
        this.surnames.add("Guerrero");
        this.surnames.add("Gonzalez");
        this.surnames.add("Baker");
        this.surnames.add("Wilson");
        this.surnames.add("Black");
        this.surnames.add("Thorsteinsson");
        this.surnames.add("Livingston");
        this.surnames.add("Piper");
        this.surnames.add("Moir");
        this.surnames.add("Stewart");
        this.surnames.add("Widner");
        this.surnames.add("Tzertzinis");
        this.surnames.add("Farrington");
        this.surnames.add("Ishikawa");
        this.surnames.add("Bronislava");
        this.surnames.add("Liouba");
        this.surnames.add("Lana");
        this.surnames.add("Genya");
        this.surnames.add("Lavrentiy");
        this.surnames.add("Burchard");
        this.surnames.add("Avdotya");
        this.surnames.add("Wandal");
        this.surnames.add("Katyusha");
        this.surnames.add("Gilbert");
        this.surnames.add("Rodion");
        this.surnames.add("Yaroslava");
        this.surnames.add("Nastasya");
        this.surnames.add("Matryona");
        this.surnames.add("Hrodulf");
        this.surnames.add("Arnifrid");

        do{
            String random = String.valueOf((int) (Math.random() * 40000000) + 500000);
            dnis.add(random);
        }while(dnis.size()<10000);

        it = dnis.iterator();


        this.DNI  = dnis.stream().collect(Collectors.toList());

    }
}
