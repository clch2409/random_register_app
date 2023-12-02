-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema longdrink_mini
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema longdrink_mini
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `longdrink_mini` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `longdrink_mini` ;

-- -----------------------------------------------------
-- Table `longdrink_mini`.`alumno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `longdrink_mini`.`alumno` (
  `id_alumno` BIGINT NOT NULL AUTO_INCREMENT,
  `activo` BIT(1) NOT NULL,
  `apellidos` VARCHAR(60) NULL DEFAULT NULL,
  `dni` VARCHAR(8) NULL DEFAULT NULL,
  `genero` VARCHAR(15) NULL DEFAULT NULL,
  `nombres` VARCHAR(60) NULL DEFAULT NULL,
  `telefono` VARCHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (`id_alumno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `longdrink_mini`.`curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `longdrink_mini`.`curso` (
  `id_curso` BIGINT NOT NULL AUTO_INCREMENT,
  `activo` BIT(1) NOT NULL,
  `nombre` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id_curso`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `longdrink_mini`.`alumno_curso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `longdrink_mini`.`alumno_curso` (
  `id_alumno` BIGINT NOT NULL,
  `id_curso` BIGINT NOT NULL,
  INDEX `FKdkjw90j8vahagoluyvyhtgiu8` (`id_curso` ASC) VISIBLE,
  INDEX `FKk5688ofdpoyd657xr61hlqws0` (`id_alumno` ASC) VISIBLE,
  CONSTRAINT `FKdkjw90j8vahagoluyvyhtgiu8`
    FOREIGN KEY (`id_curso`)
    REFERENCES `longdrink_mini`.`curso` (`id_curso`),
  CONSTRAINT `FKk5688ofdpoyd657xr61hlqws0`
    FOREIGN KEY (`id_alumno`)
    REFERENCES `longdrink_mini`.`alumno` (`id_alumno`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
