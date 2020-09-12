use utn_phones;

show indexes from calls;
create index idx_calls_dates on calls(date_call) using btree;

explain  select * from calls;
select count(*) from calls;

explain select count(*) from calls where date_call between '1950-05-10' and '2020-08-17'; --  0.219 no index, index 0.116

create index idx_phone_lines_id_person on phone_lines(id_person) using hash;

SELECT table_name AS "Table",
ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Size (MB)"
FROM information_schema.TABLES
WHERE table_schema = "utn_phones"
ORDER BY (data_length + index_length) DESC;

select * from phone_lines where id_person = 7; -- 15	1	7	3815428153	active	1

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
 
