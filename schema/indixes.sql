use utn_phones;

explain  select * from calls;
select count(*) from calls;
-- select count * sin index 0.235
-- select * from calls sin index 0.391, con index en todas las columnas tarda lo mismo
select count(*) from phone_lines;

explain select calls.duration from calls order by calls.duration asc; -- 0.281 sin index, lo mismo con index 
-- si hay una pk o fk va a usar ese index, en duration usa el index creado por mi
drop index idx_calls on calls;
create index idx_calls on calls(duration) using btree;

select count(*) from calls where date_call between '2020-05-10' and '2020-05-17'; --  0.219 no index, index 0.156
create index idx_calls_date on calls(date_call) using btree;


SELECT table_name AS "Table",
ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Size (MB)"
FROM information_schema.TABLES
WHERE table_schema = "utn_phones"
ORDER BY (data_length + index_length) DESC;


explain select plFrom.line_number as 'origin', 
                        plTo.line_number as 'destiny',
                        c.fare as 'fare',
                        c.duration as 'duration',
                        c.total_price as 'TotalPrice',
                        c.date_call as 'date'
                    from
                        calls as c
                        inner join phone_lines as plFrom on c.id_phone_line_from = plFrom.id_phone_line
                        inner join phone_lines as plTo on c.id_phone_line_to = plTo.id_phone_line
                    where
                        c.id_phone_line_from in (select id_phone_line from phone_lines where id_person = 7) and
                        c.date_call between '2000-01-01' and '2021-01-01'
                    order by
                        c.id_phone_line_from desc;

select * from phone_lines where id_person = 7; -- 15	1	7	3815428153	active	1

explain select	
		cto.city_name as "cityOrigin",
		plFrom.line_number as "phoneNumberOrigin",
		ctd.city_name as "cityDestiny",
		plTo.line_number as "phoneNumberDestiny",	
		c.duration as "duration",
		c.total_price as "totalPrice",
		c.date_call "date"
	from   
		calls as c
		inner join phone_lines as plFrom on c.id_phone_line_from = plFrom.id_phone_line 
		inner join phone_lines as plTo on c.id_phone_line_to = plTo.id_phone_line
		inner join cities as cto on cto.id_city = (select id_city from cities where plFrom.line_number like CONCAT(prefix,'%') order by LENGTH(prefix) DESC LIMIT 1)
		inner join cities as ctd on ctd.id_city = (select id_city from cities where plTo.line_number like CONCAT(prefix,'%') order by LENGTH(prefix) DESC LIMIT 1) 
		ORDER BY c.date_call;
        
create index idx_phone_line_line_number on phone_lines(line_number) using hash;
drop index idx_phone_line_line_number on phone_lines;
       explain select * from v_report
		where v_report.date BETWEEN 2000-01-01 and '2021-01-01';
create index idx_v_report_date on v_report(date) using btree;


explain select  line_number from phone_lines where id_person = 7;


call sp_show_report_by_idClient(7);

create index idx_phone_lines_id_person on phone_lines(id_person) using hash;
explain select * from v_report
where phoneNumberOrigin in (select  line_number from phone_lines where id_person = 7); 

explain select * from v_report
where 
        phoneNumberOrigin in (select  line_number from phone_lines where id_person = 7) and
        date between 2000-01-01 and '2021-01-01'; -- previo a index de cities prefix 0.997
        
        create index idx_cities_prefix on cities(prefix) using hash; -- no lo usa
        
       
explain select 
	ct.city_name as 'CityName',
	count(pl.line_number) as 'NumberOfCalls'
from 
	calls as c
    inner join phone_lines as pl on c.id_phone_line_to = pl.id_phone_line
    inner join cities as ct on ct.id_city = (select id_city from cities where pl.line_number like CONCAT(prefix,'%') order by LENGTH(prefix) DESC LIMIT 1)
	
    where 
		c.id_phone_line_from in (select  id_phone_line from phone_lines where id_person = 7)
group by
	ct.city_name
order by count(pl.line_number)
limit 
10;
 
-- idx_phone_lines_id_person, idx_calls_phone_line_from