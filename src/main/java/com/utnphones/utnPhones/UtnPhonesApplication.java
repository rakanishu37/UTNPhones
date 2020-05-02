package com.utnphones.utnPhones;

import com.utnphones.utnPhones.controllers.*;
import com.utnphones.utnPhones.dao.mysql.*;
import com.utnphones.utnPhones.domain.City;
import com.utnphones.utnPhones.domain.Client;
import com.utnphones.utnPhones.domain.Person;
import com.utnphones.utnPhones.domain.PhoneLine;
import com.utnphones.utnPhones.services.*;
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
		PhoneLineService phoneLineService = new PhoneLineService(phoneLineMySQLDao);
		PhoneLineController phoneLineController = new PhoneLineController(phoneLineService);

		//phoneLineController.getAll().forEach(phoneLine -> System.out.println(phoneLine));

		ClientMySQLDao clientMySQLDao = new ClientMySQLDao(conn, phoneLineMySQLDao, cityMySQLDao);
		ClientService clientService = new ClientService(clientMySQLDao);
		ClientController clientController = new ClientController(clientService);

		//clientController.getAll().forEach(client -> System.out.println(client));

		CallMySQLDao callMySQLDao = new CallMySQLDao(conn, phoneLineMySQLDao);
		CallService callService = new CallService(callMySQLDao);
		CallController callController = new CallController(callService);

		callController.getAll().forEach(call -> System.out.println(call.toString()));

		PersonMySQLDao personMySQLDao = new PersonMySQLDao(conn, cityMySQLDao);
		PersonService personService = new PersonService(personMySQLDao);
		PersonController personController = new PersonController(personService);

		try {
			//System.out.println(provinceController.getAll());
		}catch (RuntimeException e){

		}

	}
}
