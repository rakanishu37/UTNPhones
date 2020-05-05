package com.utnphones.utnPhones;

import com.utnphones.utnPhones.controllers.*;
import com.utnphones.utnPhones.dao.mysql.*;
import com.utnphones.utnPhones.domain.*;
import com.utnphones.utnPhones.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.query.Query;
@SpringBootApplication
public class UtnPhonesApplication {

	public static void main(String[] args) throws SQLException {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();

		Query query = session.createQuery("from Person where id_user_type = 1");

		List<Object> provinces = query.list();
		session.getTransaction().commit();

		provinces.forEach(obj -> System.out.println(obj));
		//SpringApplication.run(UtnPhonesApplication.class, args);
		/*try {
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
		LineTypeMySQLDao lineTypeMySQLDao = new LineTypeMySQLDao(conn);
		PhoneLineMySQLDao phoneLineMySQLDao = new PhoneLineMySQLDao(conn, lineTypeMySQLDao);
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

		}*/

	}
}
