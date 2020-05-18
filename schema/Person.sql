delimiter $$
create procedure sp_add_person(IN pCity_name varchar(50), IN pFirstname varchar(50), IN pSurname varchar(50), IN pDNI VARCHAR(9),IN pUsername varchar(50),IN pPassword varchar(50),IN pUserTypeId int, OUT pPersonId int)
begin
	declare vCityId int default 0;
    select id_city into vCityId from cities as c where c.city_name =  pCity_name;
    
    if(vCityId <> 0) then
		insert into persons(id_city, firstname, surname, DNI,username,password,id_user_type, is_active) values(vCityId, pFirstname, pSurname, pDNI, pUsername, pPassword, pUserTypeId, true);
        set pPersonId = last_insert_id();
	else
		signal sqlstate '10001' SET MESSAGE_TEXT = 'City does not exists', MYSQL_ERRNO = 1000 ;
    end if;
end; $$

delimiter $$
create procedure sp_add_client(IN pCity_name varchar(50), IN pFirstname varchar(50), IN pSurname varchar(50), IN pDNI VARCHAR(9),IN pUsername varchar(50),IN pPassword varchar(50), OUT pClientId int)
begin
	declare vUserTypeId int;
	declare vCounter int default 0;
	
    select count(*) into vCounter from persons as p where p.username = pUsername;
    
    if(vCounter = 0) then
		select id_user_type into vUserTypeId from user_types where user_type = "client";
		call sp_add_person(pCity_name, pFirstname, pSurname, pDNI, pUsername, pPassword,vUserTypeId,pClientId);
    end if;
end; $$

delimiter $$
create procedure sp_add_employee(IN pCity_name varchar(50), IN pFirstname varchar(50), IN pSurname varchar(50), IN pDNI VARCHAR(9),IN pUsername varchar(50),IN pPassword varchar(50), OUT pEmployeeId int)
begin
	declare vUserTypeId int;
	declare vCounter int default 0;
	
    select count(*) into vCounter from persons as p where p.username = pUsername;
    
    if(vCounter = 0) then
		select id_user_type into vUserTypeId from user_types where user_type = "employee";
		call sp_add_person(pCity_name, pFirstname, pSurname, pDNI, pUsername, pPassword,vUserTypeId,pEmployeeId);
	else
		signal sqlstate '10001' SET MESSAGE_TEXT = 'Username already exists', MYSQL_ERRNO = 2000 ;
    end if;
end; $$

delimiter $$
create trigger tbi_persons before insert on persons for each row
begin
		set new.is_active = true;
end; $$

delimiter $$
create trigger tbu_persons before update on persons for each row
begin
		if(new.is_active = false) then
			call sp_cancel_client_phone_lines(old.id_person);
		end if;
end; $$

delimiter $$
create procedure sp_cancel_client_phone_lines(IN p_id_person int)
begin
	update phone_lines set line_status = 'canceled' where phone_lines.id_person = p_id_person;
end; $$


delimiter $$
create procedure sp_show_client_calls(IN p_id_person int)
begin
select			
		plFrom.line_number,
        plTo.line_number,
		c.fare,
        c.duration,
        c.total_price,
        c.date_call
	from 
		calls as c
        inner join phone_lines as plFrom on c.id_phone_line_from = plFrom.id_phone_line
		inner join phone_lines as plTo on c.id_phone_line_to = plTo.id_phone_line
	where
		plFrom.id_person = p_id_person;
end; $$


delimiter $$
create procedure sp_show_client_calls_by_dates(IN p_id_person int, IN p_date_from date,IN p_date_to date)
begin
select			
		plFrom.line_number,
        plTo.line_number,
		c.fare,
        c.duration,
        c.total_price,
        c.date_call
	from 
		calls as c
        inner join phone_lines as plFrom on c.id_phone_line_from = plFrom.id_phone_line 
        inner join phone_lines as plTo on c.id_phone_line_to = plTo.id_phone_line		 				
	where
		plFrom.id_person = p_id_person and
        c.date_call between p_date_from and p_date_to;
end; $$



delimiter $$
create procedure sp_show_client_invoices(IN p_id_person int)
begin
	select			
		pl.line_number,
		inv.number_of_calls, 
		inv.price_cost, 
		inv.total_price, 
		inv.invoice_date, 
		inv.due_date, 
		inv.paid
	from 
		invoices as inv
		inner join phone_lines as pl on inv.id_line = pl.id_phone_line		
	where
		pl.id_person = p_id_person;
end; $$

create procedure sp_show_client_invoices_by_dates(IN p_id_person int, IN p_date_from date, IN p_date_to date)
begin
	select			
		pl.line_number,
		inv.number_of_calls, 
		inv.price_cost, 
		inv.total_price, 
		inv.invoice_date, 
		inv.due_date, 
		inv.paid
	from 
		invoices as inv
		inner join phone_lines as pl on inv.id_line = pl.id_phone_line		
	where
		pl.id_person = p_id_person and
		inv.invoice_date between p_date_from and p_date_to;
end; $$