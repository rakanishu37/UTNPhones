delimiter $$
create procedure sp_add_person(IN pCity_name varchar(50), IN pFirstname varchar(50), IN pSurname varchar(50), IN pDNI VARCHAR(9),IN pUsername varchar(50),IN pPassword varchar(50),IN pUserTypeId int, OUT pPersonId int)
begin
	declare vCityId int default 0;
    select id_city into vCityId from cities as c where c.city_name =  pCity_name;
    
    if(vCityId <> 0) then
		insert into persons(id_city, firstname, surname, DNI,username,password,id_user_type) values(vCityId, pFirstname, pSurname, pDNI, pUsername, pPassword, pUserTypeId);
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

call sp_add_client("Mar del Plata", "Susana", "Gimenez", "8448563", "susana_gim", "123456",@clientId);
call sp_add_client("La Plata", "Ricardo", "Lev", "54412658", "ricardo_montaner@hotmail.com.ar", "123456",@clientId);

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

create view v_client_public_info
as
select 
	p.firstname,
    p.surname,
    p.DNI,
    p.username, 
    c.city_name, 
    pl.line_number
from 
	persons as p 
    inner join cities as c on p.id_city = c.id_city
	inner join phone_lines as pl on p.id_person = pl.id_person and p.id_user_type = 1