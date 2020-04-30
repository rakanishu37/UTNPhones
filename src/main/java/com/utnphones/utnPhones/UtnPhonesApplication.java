package com.utnphones.utnPhones;

import com.utnphones.utnPhones.controllers.ProvinceController;
import com.utnphones.utnPhones.dao.mysql.ProvinceMySQLDao;
import com.utnphones.utnPhones.services.ProvinceService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

		ProvinceMySQLDao provinceDao = new ProvinceMySQLDao(conn);
		ProvinceService provinceService = new ProvinceService(provinceDao);
		ProvinceController provinceController = new ProvinceController(provinceService);

		try {
			System.out.println(provinceController.getAll());
		}catch (RuntimeException e){

		}

	}

}
