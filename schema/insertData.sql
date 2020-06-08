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

call sp_add_Fare("La Plata","La Plata",1);
call sp_add_Fare("Mar del Plata","Mar del Plata",1);
call sp_add_Fare("Mar del Plata","La Plata",5);
call sp_add_Fare("San Juan", "San Juan", 15);
call sp_add_Fare("Corrientes", "San Juan", 3);

insert into user_types(user_type) values ("client"),("employee");



set GLOBAL time_zone = '-3:00'   



drop trigger tbi_calls 


call sp_add_client("Mar del Plata","federico","anastasi","37753328","fede37","123",@id)
call sp_add_client("La Plata","test1","test1","11111111","test1","123",@id);

call sp_add_phone_line("home", "fede37", "4758196", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "test1", "5797650", "active", "Mar del Plata");
call sp_add_phone_line("mobile", "test1", "4888999", "active", "Bahia Blanca");

select * from phone_lines

call sp_add_call('2234758196','2235797650',30);
insert into calls
call sp_add_call('2234758196','2914888999',60);
call sp_add_call('2234758196','2214888999',300);
call sp_add_call('2214888999','2234758196',150);

