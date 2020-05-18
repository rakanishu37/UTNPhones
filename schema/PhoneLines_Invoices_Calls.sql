
set GLOBAL time_zone = '-3:00'   

delimiter //
create trigger tbi_calls before insert on calls for each row
begin
	declare vPhoneLineFrom varchar(15);
    declare vPhoneLineTo varchar(15);
    declare vFare float;
    declare vTotalPrice float;
    declare vPrefixFrom int;
    declare vPrefixTo int;
	declare vCityFromId int;
    declare vCityToId int;    
    
    
    select line_number into vPhoneLineFrom from phone_lines where id_phone_line = new.id_phone_line_from;
    
    
    select line_number into vPhoneLineTo from phone_lines where id_phone_line = new.id_phone_line_to;
    
    
    set vPrefixFrom = (select reverse(substring(reverse(vPhoneLineFrom),8)));
    set vPrefixTo = (select reverse(substring(reverse(vPhoneLineTo),8)));
	
    select id_city into vCityFromId from cities as c where c.prefix = vPrefixFrom;
    select id_city into vCityToId from cities as c where c.prefix = vPrefixTo;
    
	select price into vFare from fares where id_city_from = vCityFromId and id_city_to = vCityToId;
    set new.fare = vFare;
    set new.total_price = (new.duration * (vFare/60));
end; //

delimiter //
create procedure sp_add_phone_line(IN pLineType VARCHAR(50), IN pUsername VARCHAR(50), IN pLineNumber VARCHAR(15), IN pLineStatus ENUM('active','canceled','suspended'), IN CityName varchar(50))
begin 
	declare vPersonId int default 0;
    declare vLineTypeId int;
	declare vPhoneNumber varchar(15);
    select id_person into vPersonId from persons as p where p.username = pUsername;
    
    if(vPersonId = 0) then
		signal sqlstate '10001' SET MESSAGE_TEXT = 'Client does not exists', MYSQL_ERRNO = 3000 ;
	else
		select id_line_type into vLineTypeId from line_types as lt where lt.type_name = pLineType;
        set vPhoneNumber = (select concat(c.prefix,pLineNumber) from cities as c where c.city_name= CityName);
        
		insert into phone_lines(id_line_type,id_person,line_number,line_status) values(vLineTypeId,vPersonId, vPhoneNumber, pLineStatus);
	end if;
end; //

delimiter //
create procedure sp_add_call(IN pLineNumberFrom VARCHAR(15), IN pLineNumberTo VARCHAR(15), IN pDuration int,IN pDateCall datetime)
begin
	declare vPhoneLineFromId int;
    declare vPhoneLineToId int;
    declare vFare float;
    declare vTotalPrice float;
    declare vPrefixFrom int;
    declare vPrefixTo int;
	declare vCityFromId int;
    declare vCityToId int;
    select id_phone_line into vPhoneLineFromId from phone_lines where line_number = pLineNumberFrom;
    select id_phone_line into vPhoneLineToId from phone_lines where line_number = pLineNumberTo;
    
    set vPrefixFrom = (select reverse(substring(reverse(pLineNumberFrom),8)));
    set vPrefixTo = (select reverse(substring(reverse(pLineNumberTo),8)));
	
    select id_city into vCityFromId from cities as c where c.prefix = vPrefixFrom;
    select id_city into vCityToId from cities as c where c.prefix = vPrefixTo;
    
	select price into vFare from fares where id_city_from = vCityFromId and id_city_to = vCityToId;
    
    set vTotalPrice = (pDuration * (vFare/60));
    
	insert into calls(id_phone_line_from, id_phone_line_to, fare, duration, total_price,date_call) 
    values(vPhoneLineFromId, vPhoneLineToId, vFare, pDuration, vTotalPrice,pDateCall);
end; //

delimiter //
create procedure sp_generate_invoice()
begin
    declare vAuxId int default -1;
	declare vCallId int;
	declare vPhoneLineId int;
    declare vClientId int;
    declare vSubTotal float;
    declare vTotalPrice float default 0;
    declare vNumberOfCalls int default 0;
    declare vPriceCost float default 1.20;
	declare vInvoiceDate date;
    declare vDueDate date;
    declare vInvoiceId int;
	declare cur_calls_data_finished int default 0;
	
    Declare cur_calls_data cursor for
		select
			id_call,id_phone_line_from
		from 
			calls
		where 
			id_invoice is null
        order by
            id_phone_line_from;
    Declare continue handler for not found set cur_calls_data_finished = 1;
    Start transaction;
    set vInvoiceDate = (select current_date());
	set vDueDate = (select date_add(vInvoiceDate, INTERVAL 15 DAY));
        
    open cur_calls_data;
    
    all_clients : LOOP
		fetch cur_calls_data into vCallId, vPhoneLineId;
        if(cur_calls_data_finished = 1) then
			leave all_clients;
		end if;
        
        if(vAuxId <> vPhoneLineId) then
            select 
                (sum(total_price)*vPriceCost), count(*) 
            into 
                vTotalPrice,vNumberOfCalls 
            from 
                calls             
            where 
                id_invoice is null and id_phone_line_from = vPhoneLineId;

            insert into invoices(id_line, number_of_calls, price_cost, total_price, invoice_date, due_date, paid)
            values(vPhoneLineId,vNumberOfCalls,vPriceCost,vTotalPrice,vInvoiceDate,vDueDate,false);
            set vInvoiceId = last_insert_id();
            set vAuxId = vPhoneLineId;
        end if;
        update calls set id_invoice = vInvoiceId where id_call = vCallId;
    END LOOP all_clients;
    close cur_calls_data;    
    commit;
end; //

call sp_add_phone_line("home", "susana_gim", "4758196", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "susana_gim", "5797650", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "ricardo_@ar", "4888999", "active", "La Plata");


call sp_add_call('2234758196','2214888999',30);
call sp_add_call('2234758196','2214888999',60);
call sp_add_call('2234758196','2214888999',300);
call sp_add_call('2214888999','2234758196',150);

-- SHOW PROCESSLIST;
-- SET GLOBAL event_scheduler = ON;
delimiter //
CREATE EVENT event_generate_invoices
ON SCHEDULE EVERY "1" MONTH	
Starts "2020-06-01"
DO
begin
	call sp_generate_invoice();
end; //