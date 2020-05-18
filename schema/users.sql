

create user 'clientes'@'localhost' identified by 'clientpass';
GRANT SELECT on utn_phones.calls to 'infraestructura'@'localhost';
GRANT SELECT on utn_phones.invoices to 'infraestructura'@'localhost';

create user 'infraestructura'@'localhost' identified by 'infrapass';
GRANT INSERT(id_phone_line_from,id_phone_line_to,duration,date_call) on utn_phones.calls to 'infraestructura'@'localhost';
-- REVOKE INSERT on utn_phones.calls from 'infraestructura'@'localhost';

use utn_phones

select * from calls where id_phone_line_from in (todas sus lineas)


 -- SET PASSWORD FOR 'infraestructura'@'localhost' = PASSWORD('pruebasclaseusuarios');