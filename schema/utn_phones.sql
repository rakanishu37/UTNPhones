
CREATE DATABASE utn_phones;
USE utn_phones;
-- Por qué la llamada tiene la factura? la misma factura ya esta en la linea
CREATE TABLE Clients(
    id_client INT AUTO_INCREMENT,
    id_city INT NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL, 
    DNI VARCHAR(9) NOT NULL UNIQUE,
    CONSTRAINT PK_CLIENTS PRIMARY KEY (id_client),
    CONSTRAINT FK_CLIENTS_CITIES FOREIGN KEY (id_city) REFERENCES Cities(id_city)
);

CREATE TABLE Cities(
    id_city INT AUTO_INCREMENT,
    id_province INT NOT NULL,
    city_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_CITIES PRIMARY KEY (id_city),
    CONSTRAINT FK_CITIES_PROVINCES FOREIGN KEY (id_province) REFERENCES Provinces(id_province)
);

CREATE TABLE Provinces(
    id_province INT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_PROVINCES PRIMARY KEY (id_province)
);

CREATE TABLE User_types(
    id_user_type INT AUTO_INCREMENT,
    type_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_USER_TYPES PRIMARY KEY (id_user_type)
);

CREATE TABLE Prefixes(
    id_prefix INT AUTO_INCREMENT,
    prefix VARCHAR(5) NOT NULL,
    CONSTRAINT PK_PREFIXES PRIMARY KEY (id_prefix)
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

CREATE TABLE Phone_lines(
    id_phone_line INT AUTO_INCREMENT,
    id_user_type INT NOT NULL,
    id_client INT NOT NULL,
    id_prefix INT NOT NULL,
    line_number VARCHAR(15) NOT NULL,
    CONSTRAINT PK_PHONE_LINES PRIMARY KEY (id_phone_line),
    CONSTRAINT FK_LINES_USER_TYPES FOREIGN KEY (id_user_type) REFERENCES User_types(id_user_type),
    CONSTRAINT FK_LINES_CLIENTS FOREIGN KEY (id_client) REFERENCES Clients(id_client),
    CONSTRAINT FK_LINES_PREFIX FOREIGN KEY (id_prefix) REFERENCES Prefixes(id_prefix)
);

CREATE TABLE Calls(
    id_call INT AUTO_INCREMENT,
    id_phone_line_from INT NOT NULL,
    id_phone_line_to INT NOT NULL,
    id_fare INT NOT NULL,
    duration INT NOT NULL,
    CONSTRAINT PK_CALLS PRIMARY KEY (id_call),
    CONSTRAINT FK_CALLS_FARE FOREIGN KEY (id_fare) REFERENCES Fares(id_fare),
    CONSTRAINT FK_CALLS_PHONE_LINE_FROM FOREIGN KEY (id_phone_line_from) REFERENCES Phone_lines(id_phone_line),
    CONSTRAINT FK_CALLS_PHONE_LINE_TO FOREIGN KEY (id_phone_line_to) REFERENCES Phone_lines(id_phone_line)
);

CREATE TABLE Fares(
    id_fare INT AUTO_INCREMENT,
    id_city_from INT NOT NULL,
    id_city_to INT NOT NULL,
    price FLOAT NOT NULL,
    CONSTRAINT PK_FARES PRIMARY KEY (id_fare)
);