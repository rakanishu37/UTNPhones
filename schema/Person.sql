
drop procedure sp_show_client_callsV2
drop procedure sp_show_client_calls_by_dates



drop procedure sp_show_client_calls_by_datesV2


drop procedure sp_show_client_invoices_by_dates;

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

