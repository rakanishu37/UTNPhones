package com.utnphones.utnPhones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.SQLException;
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableAsync
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
