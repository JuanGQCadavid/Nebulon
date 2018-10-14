-- MySQL Script generated by MySQL Workbench
-- Sat 13 Oct 2018 08:15:56 PM -05
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Nebulon_spec`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Nebulon_spec` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Nebulon_spec` (
  `spec_id` INT NOT NULL,
  `spec_vendor` VARCHAR(45) NOT NULL,
  `spec_engine_code` VARCHAR(45) NOT NULL,
  `spec_size` VARCHAR(45) NULL,
  PRIMARY KEY (`spec_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'The specification of a Nebulon';


-- -----------------------------------------------------
-- Table `mydb`.`Nebulon`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Nebulon` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Nebulon` (
  `nebulon_id` INT NOT NULL,
  `nebulon_purchase_date` VARCHAR(45) NOT NULL,
  `spec_id` INT NOT NULL,
  `nebulon_state` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`nebulon_id`),
  INDEX `fk_Nebulon_spec_idx` (`spec_id` ASC),
  CONSTRAINT `fk_Nebulon_spec`
    FOREIGN KEY (`spec_id`)
    REFERENCES `mydb`.`Nebulon_spec` (`spec_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Nebulon\'s data base ';


-- -----------------------------------------------------
-- Table `mydb`.`Company`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Company` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Company` (
  `company_id` INT NOT NULL,
  `company_name` VARCHAR(45) NOT NULL,
  `company_direction` VARCHAR(45) NOT NULL,
  `company_state` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`company_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Company_contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Company_contact` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Company_contact` (
  `contact_id` INT NOT NULL,
  `contac_name` VARCHAR(45) NULL,
  `contac_number` VARCHAR(45) NULL,
  `contac_extra_info` VARCHAR(45) NULL,
  `contac_roll` VARCHAR(45) NULL,
  `company_id` INT NULL,
  PRIMARY KEY (`contact_id`),
  INDEX `fk_Company_contact_1_idx` (`company_id` ASC),
  CONSTRAINT `fk_Company_contact_1`
    FOREIGN KEY (`company_id`)
    REFERENCES `mydb`.`Company` (`company_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Staff`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Staff` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Staff` (
  `staff_id` INT NOT NULL,
  `staff_email` VARCHAR(45) NOT NULL,
  `staff_password` VARCHAR(45) NOT NULL,
  `staff_grade` INT NOT NULL,
  `staff_name` VARCHAR(45) NOT NULL,
  `staff_phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`staff_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Loan_spec`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Loan_spec` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Loan_spec` (
  `loan_spec_id` INT NOT NULL,
  `loan_starting_date` TIMESTAMP NOT NULL,
  `loan_ending_date` TIMESTAMP NOT NULL,
  `loan_state` INT NOT NULL,
  `loan_description` VARCHAR(45) NULL,
  PRIMARY KEY (`loan_spec_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Loan`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Loan` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Loan` (
  `loan_id` INT NOT NULL,
  `company_id` INT NOT NULL,
  `nebulon_id` INT NOT NULL,
  `loan_spec_id` INT NOT NULL,
  PRIMARY KEY (`loan_id`),
  INDEX `fk_Loan_company_idx` (`company_id` ASC),
  INDEX `fk_Loan_nebulon_idx` (`nebulon_id` ASC),
  INDEX `fk_Loan_loan_spec_idx` (`loan_spec_id` ASC),
  CONSTRAINT `fk_Loan_company`
    FOREIGN KEY (`company_id`)
    REFERENCES `mydb`.`Company` (`company_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_nebulon`
    FOREIGN KEY (`nebulon_id`)
    REFERENCES `mydb`.`Nebulon` (`nebulon_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loan_loan_spec`
    FOREIGN KEY (`loan_spec_id`)
    REFERENCES `mydb`.`Loan_spec` (`loan_spec_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Payment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Payment` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Payment` (
  `Payment_id` INT NOT NULL,
  `Payment_loan_id` INT NOT NULL,
  `Payment_date` TIMESTAMP NOT NULL,
  `Payment_photo` VARCHAR(45) NULL,
  `Payment_amount` INT NOT NULL,
  `Payment_description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Payment_id`),
  INDEX `fk_Payment_loan_idx` (`Payment_loan_id` ASC),
  CONSTRAINT `fk_Payment_loan`
    FOREIGN KEY (`Payment_loan_id`)
    REFERENCES `mydb`.`Loan_spec` (`loan_spec_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
