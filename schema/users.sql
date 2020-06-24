create user 'backoffice'@'localhost' identified by 'backpass'; -- manejo? de clientes, líneas y tarifas.
GRANT SELECT on utn_phones.persons to 'backoffice'@'localhost';
GRANT INSERT on utn_phones.persons to 'backoffice'@'localhost';
GRANT UPDATE on utn_phones.persons to 'backoffice'@'localhost';
GRANT SELECT on utn_phones.phone_lines to 'backoffice'@'localhost';
GRANT INSERT on utn_phones.phone_lines to 'backoffice'@'localhost';
GRANT UPDATE on utn_phones.phone_lines to 'backoffice'@'localhost';
GRANT SELECT on utn_phones.fares to 'backoffice'@'localhost';
GRANT SELECT on utn_phones.calls to 'backoffice'@'localhost';
GRANT execute on procedure utn_phones.sp_show_report_by_idClient_dates to 'backoffice'@'localhost';
GRANT execute on procedure utn_phones.sp_show_report_by_idClient to 'backoffice'@'localhost';
GRANT SELECT on utn_phones.invoices to 'clientes'@'localhost';


create user 'clientes'@'localhost' identified by 'clientpass';
GRANT SELECT on utn_phones.calls to 'clientes'@'localhost';
GRANT SELECT on utn_phones.cities to 'clientes'@'localhost';
GRANT SELECT on utn_phones.persons to 'clientes'@'localhost';
GRANT SELECT on utn_phones.invoices to 'clientes'@'localhost';
GRANT execute on procedure utn_phones.sp_show_report_by_idClient_dates to 'clientes'@'localhost';

create user 'infraestructura'@'localhost' identified by 'infrapass';
GRANT INSERT on utn_phones.calls to 'infraestructura'@'localhost';

create user 'facturacion'@'localhost' identified by 'factpass';
GRANT SELECT on utn_phones.calls to 'facturacion'@'localhost';
GRANT INSERT on utn_phones.invoices to 'facturacion'@'localhost';
GRANT execute on procedure utn_phones.sp_generate_invoice to 'facturacion'@'localhost';
GRANT EVENT ON utn_phones.invoices TO 'facturacion'@'localhost';