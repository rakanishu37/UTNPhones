CREATE DATABASE utn_phones;
USE utn_phones;

CREATE TABLE provinces(
    id_province INT AUTO_INCREMENT,
    province_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_PROVINCES PRIMARY KEY (id_province),
    CONSTRAINT UNQ_province_name unique(province_name)
);

CREATE TABLE cities(
    id_city INT AUTO_INCREMENT,
    id_province INT NOT NULL,
    city_name VARCHAR(50) NOT NULL,
    prefix varchar(5) not null,
    CONSTRAINT PK_CITIES PRIMARY KEY (id_city),
    CONSTRAINT FK_CITIES_PROVINCES FOREIGN KEY (id_province) REFERENCES provinces(id_province),
    CONSTRAINT UNQ_PREFIX unique(prefix)
);

CREATE TABLE user_types(
	id_user_type INT AUTO_INCREMENT,
    user_type VARCHAR(50),
    CONSTRAINT PK_USER_TYPES PRIMARY KEY (id_user_type),
    CONSTRAINT UNQ_USER_TYPE unique(user_type)
);

CREATE TABLE persons(
    id_person INT AUTO_INCREMENT,
    id_city INT NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL, 
    DNI VARCHAR(9) NOT  NULL,
    username varchar(50) not null,
    password varchar(50) not null,
    id_user_type INT NOT NULL, 
    is_active BOOLEAN NOT NULL,
    CONSTRAINT PK_PERSONS PRIMARY KEY (id_person),
    CONSTRAINT FK_PERSONS_CITIES FOREIGN KEY (id_city) REFERENCES cities(id_city),
    CONSTRAINT FK_PERSONS_USER_TYPES FOREIGN KEY (id_user_type) REFERENCES user_types(id_user_type),
    CONSTRAINT UNQ_DNI unique(DNI),
    CONSTRAINT UNQ_USERNAME unique(username)
);


CREATE TABLE Fares(
	id_fare INT AUTO_INCREMENT,
    id_city_from INT NOT NULL,
    id_city_to INT NOT NULL,
    price FLOAT NOT NULL,
    CONSTRAINT PK_FARES PRIMARY KEY (id_fare),
    CONSTRAINT FK_CITY_FROM_CITIES FOREIGN KEY(id_city_from) REFERENCES cities(id_city),
    CONSTRAINT FK_CITY_TO_CITIES FOREIGN KEY(id_city_to) REFERENCES cities(id_city)
);

CREATE TABLE line_types(
    id_line_type INT AUTO_INCREMENT,
    type_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_LINE_TYPES PRIMARY KEY (id_line_type),
    CONSTRAINT UNQ_TYPE_NAME unique(type_name)
);

CREATE TABLE phone_lines(
    id_phone_line INT AUTO_INCREMENT,
    id_line_type INT NOT NULL,
    id_person INT NOT NULL,
    line_number VARCHAR(15) NOT NULL,
	line_status ENUM('active', 'canceled', 'suspended') NOT NULL,
    CONSTRAINT PK_PHONE_LINES PRIMARY KEY (id_phone_line),
    CONSTRAINT FK_LINES_LINE_TYPES FOREIGN KEY (id_line_type) REFERENCES line_types(id_line_type),
    CONSTRAINT FK_LINES_PERSONS FOREIGN KEY (id_person) REFERENCES persons(id_person),
    CONSTRAINT UNQ_LINE_NUMBER UNIQUE (line_number)
);

CREATE TABLE invoices(
    id_invoice INT AUTO_INCREMENT,
    id_line INT NOT NULL,
    number_of_calls INT NOT NULL,
    price_cost FLOAT NOT NULL,
    total_price FLOAT NOT NULL,
    invoice_date date NOT NULL,
    due_date date NOT NULL,
    paid boolean,
    CONSTRAINT PK_INVOICES PRIMARY KEY (id_invoice),
    CONSTRAINT FK_INVOICES_LINES FOREIGN KEY (id_line) REFERENCES phone_lines(id_phone_line)
);

CREATE TABLE calls(
    id_call INT AUTO_INCREMENT,
    id_phone_line_from INT NOT NULL,
    id_phone_line_to INT NOT NULL,
    id_invoice int,
    fare float,
    duration INT NOT NULL,
    total_price float,
    date_call datetime,
    CONSTRAINT PK_CALLS PRIMARY KEY (id_call),
    CONSTRAINT FK_CALLS_PHONE_LINE_FROM FOREIGN KEY (id_phone_line_from) REFERENCES phone_lines(id_phone_line),
    CONSTRAINT FK_CALLS_PHONE_LINE_TO FOREIGN KEY (id_phone_line_to) REFERENCES phone_lines(id_phone_line),
    CONSTRAINT FK_CALLS_INVOICES FOREIGN KEY (id_invoice) REFERENCES invoices(id_invoice)
);