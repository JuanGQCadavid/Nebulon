CREATE DATABASE IF NOT EXISTS nebulon_database;

USE nebulon_database;

CREATE TABLE IF NOT EXISTS companies(
  id_company int(9) NOT NULL,
  cname varchar(50) NOT NULL,
  address varchar(50) NOT NULL,
  email varchar(50),
  PRIMARY KEY(id_company)
);

CREATE TABLE IF NOT EXISTS phones(
  phone varchar(30) NOT NULL,
  id_company int(9) NOT NULL,
  PRIMARY KEY(phone, id_company),
  CONSTRAINT fk_phones_id_company FOREIGN KEY(id_company) REFERENCES companies(id_company)
);

CREATE TABLE IF NOT EXISTS contracts(
  id_contract int(9) NOT NULL,
  id_company int(9) NOT NULL,
  starting_date date NOT NULL,
  ending_date date NOT NULL,
  PRIMARY KEY(id_contract),
  CONSTRAINT fk_contracts_id_company FOREIGN KEY(id_company) REFERENCES companies(id_company)
);

CREATE TABLE IF NOT EXISTS nebulizers(
  id_nebulizer int(9) NOT NULL,
  id_company int(9) NOT NULL,
  liquid_level float(10, 5),
  nstate boolean NOT NULL,
  PRIMARY KEY(id_nebulizer),
  CONSTRAINT fk_nebulizers_id_company FOREIGN KEY(id_company) REFERENCES companies(id_company)
);
