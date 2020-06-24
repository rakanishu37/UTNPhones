delimiter $$
create procedure sp_cancel_client_phone_lines(IN p_id_person int)
begin
	update phone_lines set is_active = false where phone_lines.id_person = p_id_person;
end; $$

delimiter //
create trigger tbi_persons before insert on persons for each row
begin
		set new.is_active = true;
end; //

delimiter $$
create trigger tbi_phone_lines before insert on phone_lines for each row
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

delimiter //
create trigger tbi_calls before insert on calls for each row
begin
	declare vPhoneLineFrom varchar(20);
    declare vPhoneLineTo varchar(20);
    declare vFare float;
    declare vTotalPrice float;
	declare vCityFromId int;
    declare vCityToId int;    
    
    
    select line_number into vPhoneLineFrom from phone_lines where id_phone_line = new.id_phone_line_from;
    select line_number into vPhoneLineTo from phone_lines where id_phone_line = new.id_phone_line_to;    

	select 
		id_city 
	into 
		vCityFromId
    from 
		cities 
    where 
		vPhoneLineFrom like CONCAT(prefix,'%') 
	order by 
		LENGTH(prefix) DESC 
        LIMIT 1;
    
	select 
		id_city 
	into 
		vCityToId 
    from 
		cities 
    where 
		vPhoneLineTo like CONCAT(prefix,'%') 
	order by 
		LENGTH(prefix) DESC 
        LIMIT 1;
    
	select price into vFare from fares where id_city_from = vCityFromId and id_city_to = vCityToId;
    set new.fare = vFare;
    set new.total_price = (new.duration * (vFare/60));
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

create view v_report as
	select	
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



delimiter //
create procedure sp_show_report_by_idClient(IN pIdClient int)
begin
	select 
	* 
from 
	v_report
where 
	phoneNumberOrigin in (select  line_number from phone_lines where id_person = pIdClient);
end //


delimiter //
create procedure sp_show_report_by_idClient_dates(IN pIdClient int,IN pDateFrom date, IN pDateTo date)
begin
    select 
        * 
    from 
        v_report
    where 
        phoneNumberDestiny in (select  line_number from phone_lines where id_person = pIdClient) and
        date between pDateFrom and pDateTo;
end //

--SHOW PROCESSLIST;
-- SET GLOBAL event_scheduler = ON;
delimiter //
CREATE EVENT event_generate_invoices
ON SCHEDULE EVERY "1" MONTH	
Starts "2020-06-01"
DO
begin
	call sp_generate_invoice();
end; //