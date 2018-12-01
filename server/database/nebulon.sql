CREATE DATABASE IF NOT EXISTS nebulon;

USE nebulon;

CREATE TABLE IF NOT EXISTS company(
  company_id int NOT NULL,
  company_name varchar(45) NOT NULL,
  company_address varchar(45) NOT NULL,
  company_state varchar(45),
  PRIMARY KEY(company_id)
);

CREATE TABLE IF NOT EXISTS company_contact(
  contact_id int NOT NULL,
  contact_name varchar(45) NOT NULL,
  contact_phone varchar(45) NOT NULL,
  contact_extra_info varchar(45),
  contact_roll varchar(45),
  company_id int NOT NULL,
  PRIMARY KEY(contact_id),
  CONSTRAINT fk_company_contact_company_id FOREIGN KEY(company_id) REFERENCES company(company_id)
);

CREATE TABLE IF NOT EXISTS loan(
  loan_id int NOT NULL,
  company_id int NOT NULL,
  loan_starting_date timestamp NOT NULL,
  loan_ending_date timestamp NOT NULL,
  loan_state float NOT NULL,
  loan_description varchar(45),
  PRIMARY KEY(loan_id),
  CONSTRAINT fk_loan_company_id FOREIGN KEY(company_id) REFERENCES company(company_id)
);

CREATE TABLE IF NOT EXISTS payment(
  payment_id int NOT NULL,
  loan_id int NOT NULL,
  payment_date timestamp NOT NULL,
  payment_amount float NOT NULL,
  payment_description varchar(45),
  payment_photo varchar(45),
  PRIMARY KEY(payment_id),
  CONSTRAINT fk_payment_loan_id FOREIGN KEY(loan_id) REFERENCES loan(loan_id)
);

CREATE TABLE IF NOT EXISTS nebulon_spec(
  spec_id int NOT NULL,
  spec_vendor varchar(45) NOT NULL,
  spec_engine_code varchar(45) NOT NULL,
  spec_size varchar(45),
  PRIMARY KEY(spec_id)
);

CREATE TABLE IF NOT EXISTS nebulon(
  nebulon_id int NOT NULL,
  nebulon_state varchar(45) NOT NULL,
  nebulon_liquid_level int NOT NULL,
  nebulon_schedule blob,
  nebulon_private_ip varchar(40),	
  nebulon_purchase_date timestamp NOT NULL,
  spec_id int NOT NULL,
  loan_id int NULL,
  PRIMARY KEY(nebulon_id),
  CONSTRAINT fk_nebulon_spec_id FOREIGN KEY(spec_id) REFERENCES nebulon_spec(spec_id),
  CONSTRAINT fk_nebulon_loan_id FOREIGN KEY(loan_id) REFERENCES loan(loan_id)
);
