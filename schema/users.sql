create user 'backoffice'@'localhost' identified by 'backpass'; -- manejo? de clientes, líneas y tarifas.
GRANT SELECT on utn_phones.phone_lines to 'infraestructura'@'localhost';
GRANT SELECT on utn_phones.phone_lines to 'infraestructura'@'localhost';
GRANT SELECT on utn_phones.phone_lines to 'infraestructura'@'localhost';

create user 'clientes'@'localhost' identified by 'clientpass'; -- acceso de tipo select a las tablas o solo a los 4 sp?
GRANT SELECT on utn_phones.calls to 'infraestructura'@'localhost';
GRANT SELECT on utn_phones.invoices to 'infraestructura'@'localhost';

create user 'infraestructura'@'localhost' identified by 'infrapass';
GRANT INSERT on utn_phones.calls to 'infraestructura'@'localhost';
-- REVOKE INSERT on utn_phones.calls from 'infraestructura'@'localhost';


create user 'facturacion'@'localhost' identified by 'factpass'; -- solo acceso al sp de generar facturas? pero es un programado
Grant -- s
 -- SET PASSWORD FOR 'infraestructura'@'localhost' = PASSWORD('pruebasclaseusuarios');