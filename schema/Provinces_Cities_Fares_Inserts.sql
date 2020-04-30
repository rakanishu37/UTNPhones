insert into Provinces(province_name) values
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

drop procedure sp_add_Fare

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
		insert into Fares(id_city_from,id_city_to,price) 
        values(vCityFromId,vCityToId,pPrice),(vCityToId,vCityFromId,pPrice);        
	end if;
end; //

select * from fares;
truncate fares;
call sp_add_Fare("La Plata","La Plata",1);
call sp_add_Fare("Mar del Plata","Mar del Plata",1);
call sp_add_Fare("Mar del Plata","La Plata",5);
