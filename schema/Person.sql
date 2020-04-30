drop procedure sp_add_person
delimiter $$
create procedure sp_add_person(IN pCity_name varchar(50), IN pFirstname varchar(50), IN pSurname varchar(50), IN pDNI VARCHAR(9), OUT id_person int)
begin
	declare vCityId int default 0;
    select id_city into vCityId from cities as c where c.city_name =  pCity_name;
    
    if(vCityId <> 0) then
		insert into persons(id_city, firstname, surname, DNI) values(vCityId, pFirstname, pSurname, pDNI);
        set id_person = last_insert_id();
	else
		signal sqlstate '10001' SET MESSAGE_TEXT = 'City does not exists', MYSQL_ERRNO = 1000 ;
    end if;
    
end; $$

call sp_add_person("Mar del Plata", "Pedro", "Gimenez", "18448563");
call sp_add_person("Buenos Aires", "Peter", "Perez", "42444964");

delimiter $$
create procedure sp_add_client(IN pCity_name varchar(50), IN pFirstname varchar(50), IN pSurname varchar(50), IN pDNI VARCHAR(9), IN pUsername varchar(50), IN pPassword varchar(50))
begin
	declare vPersonId int;
	declare vCounter int default 0;

    select count(*) into vCounter from clients where clients.username = pUsername;
    
    if(vCounter = 0) then
		call sp_add_person(pCity_name, pFirstname, pSurname, pDNI, vPersonId);
		insert into clients(id_person, username, password) values (vPersonId, pUsername, pPassword);
	else
		signal sqlstate '10001' SET MESSAGE_TEXT = 'Username already exists', MYSQL_ERRNO = 2000 ;
    end if;
end; $$

call sp_add_client("Catamarca", "Susana", "Gimenez", "8448563", "susana_gim", "123456");
call sp_add_client("Corrientes", "Ricardo", "Lev", "54412658", "ricardo_montaner@hotmail.com.ar", "123456");
