package com.utnphones.utnPhones;

import com.utnphones.utnPhones.controllers.ClientController;
import com.utnphones.utnPhones.controllers.PersonController;
import com.utnphones.utnPhones.controllers.ProvinceController;
import com.utnphones.utnPhones.dao.mysql.*;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.services.ClientService;
import com.utnphones.utnPhones.services.PersonService;
import com.utnphones.utnPhones.services.ProvinceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class UtnPhonesApplication {

	public static void main(String[] args) throws SQLException {
		//SpringApplication.run(UtnPhonesApplication.class, args);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/utn_phones?user=root&password=root");


		ProvinceMySQLDao provinceMySQLDao = new ProvinceMySQLDao(conn);
		ProvinceService provinceService = new ProvinceService(provinceMySQLDao);
		ProvinceController provinceController = new ProvinceController(provinceService);

		CityMySQLDao cityMySQLDao = new CityMySQLDao(conn, provinceMySQLDao);
		PhoneLineMySQLDao phoneLineMySQLDao = new PhoneLineMySQLDao(conn);

		ClientMySQLDao clientMySQLDao = new ClientMySQLDao(conn, phoneLineMySQLDao, cityMySQLDao);
		ClientService clientService = new ClientService(clientMySQLDao);
		ClientController clientController = new ClientController(clientService);

		clientController.getAll().forEach(client -> System.out.println(client));

		PersonMySQLDao personMySQLDao = new PersonMySQLDao(conn, cityMySQLDao);
		PersonService personService = new PersonService(personMySQLDao);
		PersonController personController = new PersonController(personService);




		try {
			//System.out.println(provinceController.getAll());
		}catch (RuntimeException e){

		}

	}
}
