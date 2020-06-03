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
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class UtnPhonesApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(UtnPhonesApplication.class, args);

        // TODO update y delete employees.
        // TODO Consulta de TOP 10 destinos más llamados por el usuario.
        // TODO Consulta de llamadas por usuario.
        // todo Consulta de facturas del usuario logueado por rango de fechas.

        // TODO unit test 70%

        // TODO usuarios
		// TODO 	a) BACKOFFICE, que permitirá el manejo de clientes, líneas y tarifas.
		// TODO 	b) CLIENTES, que permitirá consultas de llamadas y facturación.
		// TODO 	d) FACTURACIÓN , proceso automático de facturación.
        // TODO Este reporte incluirá
        //  i) Número de origen
        //  ii) Ciudad de origen
        //  iii) Número de destino
        //  iv) Ciudad de destino
        //  v) Precio total
        //  vi) Duración
        //  vii) Fecha y hora de llamada.

    }
}
