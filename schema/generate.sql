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
		-- set number1:=(select phone_lines.line_number from phone_lines order by rand() limit 1);
        set number1:="3815428153";
		 set number2:=(select phone_lines.line_number from phone_lines order by rand() limit 1);
		
		while number1 = number2 do
			set number2:=(select phone_lines.line_number from phone_lines order by rand() limit 1);
		end while;
		call sp_add_call(number1, number2, RAND()*(7000 - 5)+5, (NOW() - INTERVAL FLOOR(RAND() * 14) DAY)); -- hacer funcion lo de fecha
         SET counter = counter + 1;
    end while;
end; $$

call sp_generate_random_calls();




select inv.*, per.firstname, per.surname from invoices as inv
inner join phone_lines as pl on inv.id_line = pl.id_phone_line
inner join persons as per on pl.id_person = per.id_person
order by inv.invoice_date asc;

