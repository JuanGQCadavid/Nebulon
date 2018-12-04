CREATE DATABASE IF NOT EXISTS nebulon;

USE nebulon;

CREATE TABLE IF NOT EXISTS staff(
  staff_id varchar(45) NOT NULL, -- Identity card
  staff_username varchar(45) NOT NULL,
  staff_password varchar(100) NOT NULL,
  staff_level int NOT NULL, -- CEO, employee, ...
  staff_name varchar(60) NOT NULL,
  staff_phone varchar(20) NOT NULL,
  staff_email varchar(60) NOT NULL,
  PRIMARY KEY(staff_id),
  UNIQUE KEY(staff_username)
);

CREATE TABLE IF NOT EXISTS company(
  company_id int NOT NULL AUTO_INCREMENT,
  company_name varchar(45) NOT NULL,
  company_address varchar(45) NOT NULL,
  PRIMARY KEY(company_id)
);

CREATE TABLE IF NOT EXISTS company_contact(
  contact_id varchar(45) NOT NULL, -- Identity card
  contact_name varchar(45) NOT NULL,
  contact_phone varchar(45) NOT NULL,
  contact_email varchar(60) NULL,
  contact_extra_info varchar(100) NULL,
  contact_roll varchar(45) NULL,
  company_id int NOT NULL,
  PRIMARY KEY(contact_id),
  CONSTRAINT fk_company_contact_company_id FOREIGN KEY(company_id) REFERENCES company(company_id)
);

CREATE TABLE IF NOT EXISTS loan(
  loan_id int NOT NULL AUTO_INCREMENT,
  company_id int NOT NULL,
  loan_starting_date timestamp NOT NULL,
  loan_ending_date timestamp NOT NULL,
  loan_state float NOT NULL,
  loan_description varchar(45) NULL,
  PRIMARY KEY(loan_id),
  CONSTRAINT fk_loan_company_id FOREIGN KEY(company_id) REFERENCES company(company_id)
);

CREATE TABLE IF NOT EXISTS payment(
  payment_id int NOT NULL AUTO_INCREMENT,
  loan_id int NOT NULL,
  payment_date timestamp NOT NULL,
  payment_amount float NOT NULL,
  payment_description varchar(45) NULL,
  PRIMARY KEY(payment_id),
  CONSTRAINT fk_payment_loan_id FOREIGN KEY(loan_id) REFERENCES loan(loan_id)
);

CREATE TABLE IF NOT EXISTS nebulon_spec(
  spec_id int NOT NULL AUTO_INCREMENT,
  spec_vendor varchar(45) NOT NULL,
  spec_engine_code varchar(45) NOT NULL,
  PRIMARY KEY(spec_id)
);

CREATE TABLE IF NOT EXISTS nebulon(
  nebulon_id int NOT NULL,
  nebulon_state varchar(45) NULL,
  nebulon_private_ip varchar(40),	
  nebulon_purchase_date timestamp NOT NULL,
  nebulon_fg1name varchar(50) NULL,
  nebulon_fg1level int NULL,
  nebulon_fg2name varchar(50) NULL,
  nebulon_fg2level int NULL,  
  spec_id int NOT NULL,
  loan_id int NULL,
  PRIMARY KEY(nebulon_id),
  CONSTRAINT fk_nebulon_spec_id FOREIGN KEY(spec_id) REFERENCES nebulon_spec(spec_id),
  CONSTRAINT fk_nebulon_loan_id FOREIGN KEY(loan_id) REFERENCES loan(loan_id)
);
