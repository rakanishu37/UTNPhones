CREATE DATABASE utn_phones;
USE utn_phones;

CREATE TABLE Provinces(
    id_province INT AUTO_INCREMENT,
    province_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_PROVINCES PRIMARY KEY (id_province)
);

CREATE TABLE Cities(
    id_city INT AUTO_INCREMENT,
    id_province INT NOT NULL,
    city_name VARCHAR(50) NOT NULL,
    prefix varchar(5) not null,
    CONSTRAINT PK_CITIES PRIMARY KEY (id_city),
    CONSTRAINT FK_CITIES_PROVINCES FOREIGN KEY (id_province) REFERENCES Provinces(id_province),
    CONSTRAINT UNQ_PREFIX unique(prefix)
);

CREATE TABLE Persons(
    id_person INT AUTO_INCREMENT,
    id_city INT NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL, 
    DNI VARCHAR(9) NOT  NULL UNIQUE,
    CONSTRAINT PK_PERSONS PRIMARY KEY (id_person),
    CONSTRAINT FK_PERSONS_CITIES FOREIGN KEY (id_city) REFERENCES Cities(id_city)
);

CREATE TABLE Clients(
    id_client INT AUTO_INCREMENT,
    id_person int not null,
    username varchar(50) not null,
    password varchar(50) not null,
	constraint PK_CLIENTS primary key(id_client),
    constraint FK_CLIENTS_PERSONS foreign key(id_person) references Persons(id_person)
);

CREATE TABLE Employees(
	id_employee INT AUTO_INCREMENT,
    id_person int not null,
    username varchar(50) not null,
    password varchar(50) not null,
    constraint PK_EMPLOYEES primary key(id_employee),
    constraint FK_EMPLOYEES_PERSONS foreign key(id_person) references Persons(id_person)
);

CREATE TABLE Fares(
    id_city_from INT NOT NULL,
    id_city_to INT NOT NULL,
    price FLOAT NOT NULL,
    CONSTRAINT PK_FARES PRIMARY KEY (id_city_from,id_city_to),
    CONSTRAINT FK_CITY_FROM_CITIES FOREIGN KEY(id_city_from) REFERENCES Cities(id_city),
    CONSTRAINT FK_CITY_TO_CITIES FOREIGN KEY(id_city_to) REFERENCES Cities(id_city)
);

CREATE TABLE Line_types(
    id_line_type INT AUTO_INCREMENT,
    type_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_LINE_TYPES PRIMARY KEY (id_line_type)
);

CREATE TABLE Phone_lines(
    id_phone_line INT AUTO_INCREMENT,
    id_line_type INT NOT NULL,
    id_client INT NOT NULL,
    line_number VARCHAR(15) NOT NULL,
	line_status ENUM('active', 'canceled', 'suspended') NOT NULL,
    CONSTRAINT PK_PHONE_LINES PRIMARY KEY (id_phone_line),
    CONSTRAINT FK_LINES_LINE_TYPES FOREIGN KEY (id_line_type) REFERENCES Line_types(id_line_type),
    CONSTRAINT FK_LINES_CLIENTS FOREIGN KEY (id_client) REFERENCES Clients(id_client)
);

CREATE TABLE Invoices(
    id_invoice INT AUTO_INCREMENT,
    id_line INT NOT NULL,
    number_of_calls INT NOT NULL,
    price_cost FLOAT NOT NULL,
    total_price FLOAT NOT NULL,
    invoice_date TIMESTAMP(6) NOT NULL,
    due_date TIMESTAMP(6) NOT NULL, 
    CONSTRAINT PK_INVOICES PRIMARY KEY (id_invoice),
    CONSTRAINT FK_INVOICES_LINES FOREIGN KEY (id_line) REFERENCES Phone_lines(id_phone_line)
);

CREATE TABLE Calls(
    id_call INT AUTO_INCREMENT,
    id_phone_line_from INT NOT NULL,
    id_phone_line_to INT NOT NULL,
    id_invoice int not null,
    fare float NOT NULL,
    duration INT NOT NULL,
    total_price float not null,
    CONSTRAINT PK_CALLS PRIMARY KEY (id_call),
    CONSTRAINT FK_CALLS_PHONE_LINE_FROM FOREIGN KEY (id_phone_line_from) REFERENCES Phone_lines(id_phone_line),
    CONSTRAINT FK_CALLS_PHONE_LINE_TO FOREIGN KEY (id_phone_line_to) REFERENCES Phone_lines(id_phone_line),
    CONSTRAINT FK_CALLS_INVOICES FOREIGN KEY (id_invoice) REFERENCES Invoices(id_invoice)
);
