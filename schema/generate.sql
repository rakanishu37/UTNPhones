use utn_phones;

-- select count(*) from phone_lines;
-- select * from phone_lines;
drop procedure sp_generate_random_calls;
delimiter $$
create procedure sp_generate_random_calls()
begin
	declare number1 varchar(15);
    declare number2 varchar(15);
    declare counter int default 0;
    
    while counter < 1000 do
		set number1:=(select phone_lines.line_number from phone_lines order by rand() limit 1);
		set number2:=(select phone_lines.line_number from phone_lines order by rand() limit 1);
		
		while number1 = number2 do
			set number2:=(select phone_lines.line_number from phone_lines order by rand() limit 1);
		end while;
		call sp_add_call(number1, number2, RAND()*(7000 - 5)+5, (NOW() - INTERVAL FLOOR(RAND() * 14) DAY)); -- hacer funcion lo de fecha
         SET counter = counter + 1;
    end while;
end; $$

call sp_generate_random_calls();

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



