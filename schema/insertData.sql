set GLOBAL time_zone = '-3:00';

insert into Provinces(province_name) values
('test'),
('Buenos Aires'),
('Catamarca'),
('Chaco'),
('Chubut'),
('Córdoba'),
('Corrientes'),
('Entre Ríos'),
('Formosa'),
('Jujuy'),
('La Pampa'),
('La Rioja'),
('Mendoza'),
('Misiones'),
('Neuquén'),
('Río Negro'),
('Salta'),
('San Juan'),
('San Luis'),
('Santa Cruz'),
('Santa Fé'),
('Santiago del Estero'),
('Tierra del Fuego'),
('Tucumán');

delimiter //
create procedure sp_add_city(IN pProvince_name varchar(50),IN pCity_name varchar(50),IN pPrefix varchar(5))
begin
    declare vProvinceId int;
    select id_province into vProvinceId from Provinces as p where p.province_name = pProvince_name;
    insert into Cities(id_province,city_name,prefix) values(vProvinceId,pCity_name,pPrefix);
end; //

call sp_add_city('test','test','00');
call sp_add_city('Buenos Aires','Buenos Aires','11');
call sp_add_city('Buenos Aires','La Plata','221');
call sp_add_city('Buenos Aires','Mar del Plata','223');
call sp_add_city('Buenos Aires','Bahía Blanca','291');
call sp_add_city('Catamarca','Catamarca','3833');
call sp_add_city('Córdoba','Córdoba','351');
call sp_add_city('Corrientes','Corrientes','3783');
call sp_add_city('Chaco','Resistencia','3722');
call sp_add_city('Chubut','Rawson','2965');
call sp_add_city('Entre Ríos','Paraná','343');
call sp_add_city('Formosa','Formosa','3717');
call sp_add_city('Jujuy','San Salvador de Jujuy','388');
call sp_add_city('La Pampa','Santa Rosa','2954');
call sp_add_city('La Rioja','La Rioja','3822');
call sp_add_city('Mendoza','Mendoza','261');
call sp_add_city('Misiones','Posadas','3752');
call sp_add_city('Neuquén','Neuquén','299');
call sp_add_city('Río Negro','Viedma','2920');
call sp_add_city('Salta','Salta','387');
call sp_add_city('San Juan','San Juan','264');
call sp_add_city('San Luis','San Luis','2652');
call sp_add_city('Santa Cruz','Río Gallegos','2966');
call sp_add_city('Santa Fé','Santa Fé','342');
call sp_add_city('Santa Fé','Rosario','341');
call sp_add_city('Santiago del Estero','Santiago del Estero','385');
call sp_add_city('Tucumán','San Miguel de Tucumán','381');
call sp_add_city('Tierra del Fuego','Ushuaia','2901');

insert into line_types(type_name) values('mobile'),('home');

delimiter //
create procedure sp_add_Fare(IN pCity_name_From varchar(50),IN pCity_name_To varchar(50),IN pPrice float)
begin
    declare vCityFromId int;
    declare vCityToId int;

    select id_city into vCityFromId from cities as c where c.city_name =  pCity_name_From;
    select id_city into vCityToId from cities as c where c.city_name = pCity_name_To;

    if(vCityFromId = vCityToId) then
		insert into Fares(id_city_from,id_city_to,price) values(vCityFromId,vCityToId,pPrice);
    else
		insert into Fares(id_city_from,id_city_to,price) values
        (vCityFromId,vCityToId,pPrice),
        (vCityToId,vCityFromId,pPrice);
	end if;
end; //

call sp_add_Fare("La Plata","La Plata",1);
call sp_add_Fare("Mar del Plata","Mar del Plata",1);
call sp_add_Fare("Mar del Plata","La Plata",5);
call sp_add_Fare("San Juan", "San Juan", 15);
call sp_add_Fare("Corrientes", "San Juan", 3);

insert into user_types(user_type) values ("client"),("employee");


-- drop trigger tbi_calls 

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

call sp_add_client("Mar del Plata","federico","anastasi","37753328","fede37","123",@id);
call sp_add_employee("test","test","test","99","test","test",@id);
call sp_add_client("La Plata","test1","test1","11111111","test1","123",@id);
select * from persons

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
call sp_add_phone_line("home", "fede37", "4758196", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "test1", "5797650", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "test1", "4888999", "active", "Bahia Blanca");

select * from persons;
select * from phone_lines;
select * from calls;
select * from invoices;

/*call sp_add_call('2234758196','2235797650',30);
call sp_add_call('2234758196','2914888999',60);
call sp_add_call('2234758196','2214888999',300);
call sp_add_call('2214888999','2234758196',150);*/