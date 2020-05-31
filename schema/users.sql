create user 'backoffice'@'localhost' identified by 'backpass'; -- manejo? de clientes, líneas y tarifas.
GRANT SELECT on utn_phones.phone_lines to 'infraestructura'@'localhost';
GRANT SELECT on utn_phones.phone_lines to 'infraestructura'@'localhost';
GRANT SELECT on utn_phones.phone_lines to 'infraestructura'@'localhost';

create user 'clientes'@'localhost' identified by 'clientpass'; -- acceso de tipo select a las tablas o solo a los 4 sp?
GRANT SELECT on utn_phones.calls to 'infraestructura'@'localhost';
GRANT SELECT on utn_phones.invoices to 'infraestructura'@'localhost';

create user 'infraestructura'@'localhost' identified by 'infrapass';
GRANT INSERT(id_phone_line_from,id_phone_line_to,duration,date_call) on utn_phones.calls to 'infraestructura'@'localhost';
-- REVOKE INSERT on utn_phones.calls from 'infraestructura'@'localhost';


create user 'facturacion'@'localhost' identified by 'factpass'; -- solo acceso al sp de generar facturas? pero es un programado

 -- SET PASSWORD FOR 'infraestructura'@'localhost' = PASSWORD('pruebasclaseusuarios');


-- top10 destinos mas llamados de todos los clientes
select 
	ct.city_name,
	count(pl.line_number) as 'cant de veces llamado'
from 
	calls as c
    inner join phone_lines as pl on c.id_phone_line_to = pl.id_phone_line
    inner join cities as ct on ct.id_city = (select id_city from cities where pl.line_number like CONCAT(prefix,'%') order by LENGTH(prefix) DESC LIMIT 1)
group by
	pl.line_number
limit 
	10;

    
-- cant de veces que fue llamado y desde donde el cliente en cuestion
select 	
	ct.city_name,
	count(pl.line_number) as 'cant de veces llamado'
from 
	calls as c
    inner join phone_lines as pl on c.id_phone_line_to = pl.id_phone_line
    inner join persons as p on pl.id_person = p.id_person
    inner join cities as ct on ct.id_city = p.id_city
where 
	p.id_person = 1
group by
	pl.id_person ;


select 	
	/*ct.city_name,
	count(plt.line_number) as 'cant de veces llamado'*/
    *
from 
	calls as c
    inner join phone_lines as plf on c.id_phone_line_from = plf.id_phone_line
    inner join phone_lines as plt on c.id_phone_line_to = plt.id_phone_line
    inner join persons as pf on plf.id_person = pf.id_person
    inner join persons as pt on plt.id_phone_line = pt.id_person
    inner join cities as ct on ct.id_city = pt.id_city
where 
	pf.id_person = 1
group by
	plt.id_person
limit 
	10;