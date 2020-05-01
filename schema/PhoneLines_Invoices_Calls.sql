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
drop procedure sp_add_call
delimiter //
create procedure sp_add_call(IN pLineNumberFrom VARCHAR(15), IN pLineNumberTo VARCHAR(15), IN pDuration int)
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
	insert into calls(id_phone_line_from, id_phone_line_to, fare, duration, total_price) 
    values(vPhoneLineFromId, vPhoneLineToId, vFare, pDuration, vTotalPrice);
end; //

delimiter //
create procedure sp_add_invoice()
begin

end; //


call sp_add_phone_line("home", "susana_gim", "4758196", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "susana_gim", "5797650", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "ricardo_montaner@hotmail.com.ar", "4888999", "active", "La Plata");

select * from phone_lines;
select * from v_cities_fares;
select * from v_client_public_info;
call sp_add_call('2234758196','2235797650',30);
select * from invoices;
/*insert into calls(id_phone_line_from, id_phone_line_to, fare, duration, total_price) 
    values(1, 2, 5, 1000, 5000);*/