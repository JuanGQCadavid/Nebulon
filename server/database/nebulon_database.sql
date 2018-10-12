CREATE DATABASE IF NOT EXISTS nebulon_database;

USE nebulon_database;

CREATE TABLE IF NOT EXISTS administrator(
  id_administrator varchar(20) NOT NULL,
  aname varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  phones varchar(150) NOT NULL,
  PRIMARY KEY(id_administrator)
);

CREATE TABLE IF NOT EXISTS contract(
  id_contract int(9) NOT NULL,
  starting_date date NOT NULL,
  ending_date date NOT NULL,
  id_administrator varchar(20) NOT NULL,
  PRIMARY KEY(id_contract),
  CONSTRAINT fk_contract_id_administrator FOREIGN KEY(id_administrator) REFERENCES administrator(id_administrator)
);

CREATE TABLE IF NOT EXISTS companie(
  id_company int(9) NOT NULL,
  cname varchar(50) NOT NULL,
  address varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  phones varchar(150) NOT NULL,
  id_contract int(9) NOT NULL,
  PRIMARY KEY(id_company),
  CONSTRAINT fk_companie_id_contract FOREIGN KEY(id_contract) REFERENCES contract(id_contract)  
);

CREATE TABLE IF NOT EXISTS nebulizer(
  id_nebulizer int(9) NOT NULL,
  liquid_level float(10, 5),
  nstate boolean NOT NULL,
  id_contract int(9),
  PRIMARY KEY(id_nebulizer),
  CONSTRAINT fk_nebulizer_id_contract FOREIGN KEY(id_contract) REFERENCES contract(id_contract)
);
