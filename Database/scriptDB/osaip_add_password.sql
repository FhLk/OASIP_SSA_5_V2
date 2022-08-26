-- MySQL Script generated by MySQL Workbench
-- Thu Aug 25 19:26:53 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema oasip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema oasip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `oasip` DEFAULT CHARACTER SET utf8 ;
-- -----------------------------------------------------
-- Schema oasip
-- -----------------------------------------------------
USE `oasip` ;

-- -----------------------------------------------------
-- Table `oasip`.`eventcategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oasip`.`eventcategory` (
  `category_id` INT NOT NULL,
  `category_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(550) NULL,
  `duration` INT NOT NULL,
  PRIMARY KEY (`category_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oasip`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oasip`.`user` (
  `name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(50) NOT NULL,
  `role` ENUM('ADMIN', 'LECTURER', 'STUDENT') NOT NULL,
  `update_on` DATETIME NOT NULL,
  `created_on` DATETIME NOT NULL,
  `password` VARCHAR(14) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oasip`.`eventbooking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oasip`.`eventbooking` (
  `booking_id` INT NOT NULL,
  `booking_name` VARCHAR(150) NOT NULL,
  `booking_email` VARCHAR(150) NOT NULL,
  `start_time` DATETIME NOT NULL,
  `booking_duration` INT NOT NULL,
  `event_note` VARCHAR(550) NULL,
  `category` INT NOT NULL,
  `user_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`booking_id`),
  INDEX `fk_eventbooking_eventcategory_idx` (`category` ASC) VISIBLE,
  INDEX `fk_eventbooking_user1_idx` (`user_name` ASC) VISIBLE,
  CONSTRAINT `fk_eventbooking_eventcategory`
    FOREIGN KEY (`category`)
    REFERENCES `oasip`.`eventcategory` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_eventbooking_user1`
    FOREIGN KEY (`user_name`)
    REFERENCES `oasip`.`user` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `oasip`.`lecturer_event_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `oasip`.`lecturer_event_category` (
  `eventcategory_id` INT NOT NULL,
  `user_lecturer` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`eventcategory_id`, `user_lecturer`),
  INDEX `fk_eventcategory_has_user_user1_idx` (`user_lecturer` ASC) VISIBLE,
  INDEX `fk_eventcategory_has_user_eventcategory1_idx` (`eventcategory_id` ASC) VISIBLE,
  CONSTRAINT `fk_eventcategory_has_user_eventcategory1`
    FOREIGN KEY (`eventcategory_id`)
    REFERENCES `oasip`.`eventcategory` (`category_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_eventcategory_has_user_user1`
    FOREIGN KEY (`user_lecturer`)
    REFERENCES `oasip`.`user` (`name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
