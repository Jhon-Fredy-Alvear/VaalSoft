-- MySQL Workbench Synchronization
-- Generated: 2015-03-11 13:38
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: cpe

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

ALTER SCHEMA `coomeva`  DEFAULT CHARACTER SET latin1  DEFAULT COLLATE latin1_spanish_ci ;

CREATE TABLE IF NOT EXISTS `coomeva`.`Cliente` (
  `idCliente` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código del Cliente.',
  `idTipo_De_Credito` INT(2) NOT NULL COMMENT 'Campo que guarda la llave foranea de la Tabla Tipo de credito.',
  `idCreditoEmprendimiento` BIGINT(15) NULL DEFAULT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Credito Emprendimiento.',
  `idCreditoFortalecimiento` BIGINT(15) NULL DEFAULT NULL COMMENT 'Campo que guardar la llave foránea de la tabla Credito Fortalecimiento.',
  `idRegistro_Cliente` BIGINT(15) NOT NULL COMMENT 'Campo que guardar la llave foránea de la tabla Registro Cliente.',
  `idSalario` INT(2) NULL DEFAULT NULL COMMENT 'Campo que guardar la llave foránea de la Salario.',
  `idGenero` INT(2) NOT NULL COMMENT 'Campo que guardar la llave foránea de la tabla Genero.',
  `Barrio_idBarrio` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Barrio.',
  `idObservacion` BIGINT(15) NULL DEFAULT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Observación.',
  `idRango_Monto` BIGINT(15) NOT NULL COMMENT 'Campo que guardar la llave foránea de la tabla Rango Monto.',
  `idCiudad` BIGINT(4) NOT NULL COMMENT 'Campo que guardar la llave foránea de la tabla Ciudad.',
  `idEmpresa` BIGINT(15) NULL DEFAULT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Empresa.',
  `idFiador` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Fiador.',
  `idDepartamento` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Departamento.',
  `idRegional` BIGINT(15) NOT NULL COMMENT 'Campo que guardar la llave foránea de la tabla Regional.',
  `nombreCliente` VARCHAR(25) NOT NULL COMMENT 'Campo que guardara el primer nombre del Cliente.',
  `nombreCliente2` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Campo que guardara el segundo nombre del Cliente.',
  `apellidoCliente` VARCHAR(25) NOT NULL COMMENT 'Campo que guardara el primer apellido del Cliente.',
  `apellidoCliente2` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Campo que guardara el Segundo Apellido del Cliente.',
  `fechaNacimientoCliente` DATE NOT NULL COMMENT 'Campo que guardara la Fecha de nacimiento del cliente.',
  `cedulaCliente` BIGINT(12) NOT NULL COMMENT 'Campo que guardara la cédula de ciudadanía del cliente.',
  `telefonoCliente` BIGINT(8) NOT NULL COMMENT 'Campo que guardara el numero de teléfono del cliente.',
  `celularCliente` BIGINT(10) NULL DEFAULT NULL COMMENT 'Campo que guardara el numero de  Celular del cliente.',
  `direccionCliente` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara la Dirección del cliente.',
  `emailCliente` VARCHAR(35) NOT NULL COMMENT 'Campo que guardara el correo electrónico del cliente.',
  `cargoEmprendimiento` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Campo que guardara el Cargo que ocupa el Cliente Emprendimiento.',
  `asociadoCoomeva` TINYINT(1) NOT NULL COMMENT 'Campo que guardara si el cliente está asociado a coomeva.',
  PRIMARY KEY (`idCliente`),
  INDEX `fk_Cliente_CreditoEmprendimiento_idx` (`idCreditoEmprendimiento` ASC),
  INDEX `fk_Cliente_Salario1_idx` (`idSalario` ASC),
  INDEX `fk_Cliente_Registro_Cliente1_idx` (`idRegistro_Cliente` ASC),
  INDEX `fk_Cliente_CreditoFortalecimiento1_idx` (`idCreditoFortalecimiento` ASC),
  INDEX `fk_Cliente_Genero1_idx` (`idGenero` ASC),
  INDEX `fk_Cliente_Observacion1_idx` (`idObservacion` ASC),
  INDEX `fk_Cliente_Rango_Monto1_idx` (`idRango_Monto` ASC),
  INDEX `fk_Cliente_Ciudad1_idx` (`idCiudad` ASC),
  INDEX `fk_Cliente_Empresa1_idx` (`idEmpresa` ASC),
  INDEX `fk_Cliente_Fiador1_idx` (`idFiador` ASC),
  INDEX `fk_Cliente_Departamento1_idx` (`idDepartamento` ASC),
  INDEX `fk_Cliente_Regional1_idx` (`idRegional` ASC),
  INDEX `fk_Cliente_Barrio1_idx` (`Barrio_idBarrio` ASC),
  INDEX `fk_Cliente_Tipo_De_Credito1_idx` (`idTipo_De_Credito` ASC),
  CONSTRAINT `fk_Cliente_CreditoEmprendimiento`
    FOREIGN KEY (`idCreditoEmprendimiento`)
    REFERENCES `coomeva`.`CreditoEmprendimiento` (`idCreditoEmprendimiento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Salario1`
    FOREIGN KEY (`idSalario`)
    REFERENCES `coomeva`.`Salario` (`idSalario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Registro_Cliente1`
    FOREIGN KEY (`idRegistro_Cliente`)
    REFERENCES `coomeva`.`Registro_Cliente` (`idRegistro_Cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_CreditoFortalecimiento1`
    FOREIGN KEY (`idCreditoFortalecimiento`)
    REFERENCES `coomeva`.`CreditoFortalecimiento` (`idCreditoFortalecimiento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Genero1`
    FOREIGN KEY (`idGenero`)
    REFERENCES `coomeva`.`Genero` (`idGenero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Observacion1`
    FOREIGN KEY (`idObservacion`)
    REFERENCES `coomeva`.`Observacion` (`idObservacion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Rango_Monto1`
    FOREIGN KEY (`idRango_Monto`)
    REFERENCES `coomeva`.`Rango_Monto` (`idRango_Monto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Ciudad1`
    FOREIGN KEY (`idCiudad`)
    REFERENCES `coomeva`.`Ciudad` (`idCiudad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Empresa1`
    FOREIGN KEY (`idEmpresa`)
    REFERENCES `coomeva`.`Empresa` (`idEmpresa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Fiador1`
    FOREIGN KEY (`idFiador`)
    REFERENCES `coomeva`.`Fiador` (`idFiador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Departamento1`
    FOREIGN KEY (`idDepartamento`)
    REFERENCES `coomeva`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Regional1`
    FOREIGN KEY (`idRegional`)
    REFERENCES `coomeva`.`Regional` (`idRegional`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Barrio1`
    FOREIGN KEY (`Barrio_idBarrio`)
    REFERENCES `coomeva`.`Barrio` (`idBarrio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cliente_Tipo_De_Credito1`
    FOREIGN KEY (`idTipo_De_Credito`)
    REFERENCES `coomeva`.`Tipo_De_Credito` (`idTipo_De_Credito`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que llevará el registro de los datos de los  clientes Fortalecimiento y Emprendimiento.';

CREATE TABLE IF NOT EXISTS `coomeva`.`CreditoEmprendimiento` (
  `idCreditoEmprendimiento` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código credito Emprendimiento.',
  `ideaNegocio` VARCHAR(1000) NOT NULL COMMENT 'Campo que guardara la idea de negocio del cliente emprendimiento.',
  `fechaEmprendimiento` DATE NOT NULL COMMENT 'Campo que guardará la fecha del crédito Emprendimiento.',
  PRIMARY KEY (`idCreditoEmprendimiento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará la información del crédito Emprendimiento.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Barrio` (
  `idBarrio` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código del Barrio.',
  `barrio` VARCHAR(25) NOT NULL COMMENT 'Campo que guardara el nombre del barrio.',
  PRIMARY KEY (`idBarrio`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará los barrios de la ciudad.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Salario` (
  `idSalario` INT(2) NOT NULL COMMENT 'Campo que guardar el Código del salario.',
  `salario` DECIMAL(19,2) NOT NULL COMMENT 'Campo que guardara el salario del Cliente emprendimiento.',
  PRIMARY KEY (`idSalario`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará los diferentes salarios del Cliente Emprendimiento.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Registro_Cliente` (
  `idRegistro_Cliente` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código de registro.',
  `cartaLaboral` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara la carta laboral del cliente.',
  `desprendibleDePago` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara los desprendibles de pago del cliente.',
  `contrato` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara documento de contrato.',
  `declaracionRenta` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara documento de declaración de renta del cliente.',
  `visitaNegocio` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara documento de visita realizada al negocio. ',
  PRIMARY KEY (`idRegistro_Cliente`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará la documentación del cliente Fortalecimiento y Emprendimiento.';

CREATE TABLE IF NOT EXISTS `coomeva`.`CreditoFortalecimiento` (
  `idCreditoFortalecimiento` BIGINT(15) NOT NULL COMMENT 'Campo que guardara el Código credito fortalecimiento.',
  `ideaFortalecimiento` VARCHAR(1000) NOT NULL COMMENT 'Campo que guardara la idea de fortalecimiento.',
  `antiguedadNegocio` VARCHAR(15) NOT NULL COMMENT 'Campo que guardar la antigüedad de la empresa.',
  PRIMARY KEY (`idCreditoFortalecimiento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará la información del crédito Fortalecimiento.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Tipo_De_Credito` (
  `idTipo_De_Credito` INT(2) NOT NULL COMMENT 'Campo que guardará el Código tipo de crédito.',
  `nombreTipoDeCredito` VARCHAR(25) NOT NULL COMMENT 'Campo que guardara el nombre del tipo de crédito.',
  PRIMARY KEY (`idTipo_De_Credito`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guarda los tipos de crédito de Fundación Coomeva.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Genero` (
  `idGenero` INT(2) NOT NULL COMMENT 'Campo que guardar el Código del genero.',
  `nombreGenero` VARCHAR(15) NOT NULL COMMENT 'Campo que guardara el nombre del género.',
  PRIMARY KEY (`idGenero`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará el género del Cliente.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Rango_Cuota` (
  `idRango_Cuota` INT(3) NOT NULL COMMENT 'Campo que guardará el Código del rango cuota.',
  `cuotas` INT(4) NOT NULL COMMENT 'campo que guardará el número de cuotas del crédito.',
  PRIMARY KEY (`idRango_Cuota`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla donde se guardara los diferentes rangos de cuota de los créditos.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Tipo_de_Observacion` (
  `idTipo_de_Observacion` INT(2) NOT NULL COMMENT 'Campo que guardará el Código tipo de observación.',
  `tipoObservacionCliente` VARCHAR(25) NOT NULL COMMENT 'Campo que guardara el nombre del tipo de observación.',
  PRIMARY KEY (`idTipo_de_Observacion`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará los tipos de observación.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Observacion` (
  `idObservacion` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código observación.',
  `idTipo_de_Observacion` INT(2) NOT NULL COMMENT 'Campo que guardar la llave foránea de la tabla tipo observación.',
  `observacionCliente` VARCHAR(250) NOT NULL COMMENT 'Campo que guardara la observación realizada al cliente.',
  `fechaObservacion` DATE NOT NULL COMMENT 'Campo que guardará la fecha de la observación.',
  PRIMARY KEY (`idObservacion`),
  INDEX `fk_Observacion_Tipo_de_Observacion1_idx` (`idTipo_de_Observacion` ASC),
  CONSTRAINT `fk_Observacion_Tipo_de_Observacion1`
    FOREIGN KEY (`idTipo_de_Observacion`)
    REFERENCES `coomeva`.`Tipo_de_Observacion` (`idTipo_de_Observacion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que llevará las observaciones del crédito  de los clientes.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Rango_Monto` (
  `idRango_Monto` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código rango monto.',
  `idRango_Cuota` INT(3) NOT NULL COMMENT 'Campo que guardar la llave foránea de la Tabla Rango Cuota.',
  `monto` DECIMAL(19,2) NOT NULL COMMENT 'campo que guardara el monto del credito.',
  `fechaDelMonto` DATE NOT NULL COMMENT 'Campo que guardará la fecha en que se asignó el monto.',
  PRIMARY KEY (`idRango_Monto`),
  INDEX `fk_Rango_Monto_Rango_Cuota1_idx` (`idRango_Cuota` ASC),
  CONSTRAINT `fk_Rango_Monto_Rango_Cuota1`
    FOREIGN KEY (`idRango_Cuota`)
    REFERENCES `coomeva`.`Rango_Cuota` (`idRango_Cuota`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará los diferentes montos de cada cliente.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Ciudad` (
  `idCiudad` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código de la ciudad.',
  `nombreCiudad` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara el nombre de la ciudad.',
  PRIMARY KEY (`idCiudad`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará las ciudades de los departamentos.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Empresa` (
  `idEmpresa` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código de la empresa.',
  `idDepartamento` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Departamento.',
  `idCiudad` BIGINT(4) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Ciudad.',
  `idBarrio` BIGINT(4) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Barrio.',
  `nombreEmpresa` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara el nombre de la Empresa.',
  `nitEmpresa` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara el Nit de la Empresa.',
  `direccionEmpresa` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara la dirección de la Empresa.',
  `telefonoEmpresa` BIGINT(9) NOT NULL COMMENT 'Campo que guardara el teléfono de la Empresa.',
  `emailEmpresa` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara el correo electrónico de la Empresa.',
  PRIMARY KEY (`idEmpresa`),
  INDEX `fk_Empresa_Departamento1_idx` (`idDepartamento` ASC),
  INDEX `fk_Empresa_Ciudad1_idx` (`idCiudad` ASC),
  INDEX `fk_Empresa_Barrio1_idx` (`idBarrio` ASC),
  CONSTRAINT `fk_Empresa_Departamento1`
    FOREIGN KEY (`idDepartamento`)
    REFERENCES `coomeva`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empresa_Ciudad1`
    FOREIGN KEY (`idCiudad`)
    REFERENCES `coomeva`.`Ciudad` (`idCiudad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empresa_Barrio1`
    FOREIGN KEY (`idBarrio`)
    REFERENCES `coomeva`.`Barrio` (`idBarrio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará los datos de la empresa cliente fortalecimiento.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Fiador` (
  `idFiador` BIGINT(15) NOT NULL COMMENT 'Campo que guardara el Código del fiador.',
  `idCiudad` BIGINT(15) NOT NULL COMMENT 'campo que  guarda la llave foránea de la tabla Ciudad.',
  `idDepartamento` BIGINT(15) NOT NULL COMMENT 'Campo que guarda la llave foránea de la tabla Departamento.',
  `idBarrio` BIGINT(15) NOT NULL COMMENT 'Campo que guarda la llave foránea de la tabla Barrio.',
  `idGenero` INT(2) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Genero.',
  `nombreFiador` VARCHAR(25) NOT NULL COMMENT 'Campo que guardara el primer nombre del Fiador.',
  `nombreFiador2` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Campo que guardara el segundo nombre del Fiador.',
  `apellidoFiador` VARCHAR(25) NOT NULL COMMENT 'Campo que guardara el primer apellido del Fiador.',
  `apellidoFiador2` VARCHAR(25) NULL DEFAULT NULL COMMENT 'Campo que guardara el segundo apellido del Fiador.',
  `fechaNacimientoFiador` DATE NOT NULL COMMENT 'Campo que guardará la fecha de nacimiento del Fiador.',
  `cedulaFiador` BIGINT(12) NOT NULL COMMENT 'Campo que guardara la cédula del Fiador.',
  `telefonoFiador` BIGINT(8) NOT NULL COMMENT 'Campo que gurdara el numero de telefono del Fiador.',
  `celularFiador` BIGINT(10) NULL DEFAULT NULL COMMENT 'Campo que gurdara el numero de celular del Fiador.',
  `direccionFiador` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara la dirección del Fiador.',
  `asociadocoomevaFiador` TINYINT(1) NOT NULL COMMENT 'Campo que guardara si el fiador es asociado a Coomeva.',
  PRIMARY KEY (`idFiador`),
  INDEX `fk_Fiador_Ciudad1_idx` (`idCiudad` ASC),
  INDEX `fk_Fiador_Departamento1_idx` (`idDepartamento` ASC),
  INDEX `fk_Fiador_Barrio1_idx` (`idBarrio` ASC),
  INDEX `fk_Fiador_Genero1_idx` (`idGenero` ASC),
  CONSTRAINT `fk_Fiador_Ciudad1`
    FOREIGN KEY (`idCiudad`)
    REFERENCES `coomeva`.`Ciudad` (`idCiudad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Fiador_Departamento1`
    FOREIGN KEY (`idDepartamento`)
    REFERENCES `coomeva`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Fiador_Barrio1`
    FOREIGN KEY (`idBarrio`)
    REFERENCES `coomeva`.`Barrio` (`idBarrio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Fiador_Genero1`
    FOREIGN KEY (`idGenero`)
    REFERENCES `coomeva`.`Genero` (`idGenero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guarda la información del fiador del cliente.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Departamento` (
  `idDepartamento` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código del departamento.',
  `nombreDepartamento` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara el nombre del departamento.',
  PRIMARY KEY (`idDepartamento`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará los departamentos del país.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Regional` (
  `idRegional` BIGINT(15) NOT NULL COMMENT 'Campo que guardara el Código de la Regional.',
  `idDepartamento` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la Tabla Departamento. ',
  `idCiudad` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la Tabla Ciudad.',
  `idBarrio` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la Tabla Barrio.',
  `nombreRegional` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara el nombre de la Regional.',
  PRIMARY KEY (`idRegional`),
  INDEX `fk_Regional_Departamento1_idx` (`idDepartamento` ASC),
  INDEX `fk_Regional_Ciudad1_idx` (`idCiudad` ASC),
  INDEX `fk_Regional_Barrio1_idx` (`idBarrio` ASC),
  CONSTRAINT `fk_Regional_Departamento1`
    FOREIGN KEY (`idDepartamento`)
    REFERENCES `coomeva`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Regional_Ciudad1`
    FOREIGN KEY (`idCiudad`)
    REFERENCES `coomeva`.`Ciudad` (`idCiudad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Regional_Barrio1`
    FOREIGN KEY (`idBarrio`)
    REFERENCES `coomeva`.`Barrio` (`idBarrio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará la información de las regionales de Fundación Coomeva.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Empleado` (
  `idEmpleado` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código del Empleado.',
  `idRegional` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la Tabla Regional.',
  `idDepartamento` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Departamento.',
  `idCiudad` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Ciudad.',
  `idBarrio` BIGINT(15) NOT NULL COMMENT 'Campo que guardara la llave foranea de Tabla Barrio.',
  `idRol` INT(3) NOT NULL COMMENT 'Campo que guardara la llave foránea de la Tabla Rol.',
  `idGenero` INT(2) NOT NULL COMMENT 'Campo que guardara la llave foránea de la Tabla Genero.',
  `emailEmpleado` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara el correo electrónico del Empleado.',
  `contraseniaEmpleado` VARCHAR(25) NOT NULL COMMENT 'Campo que guardará la contraseña del Empleado.',
  `nombreEmpleado` VARCHAR(15) NOT NULL COMMENT 'Campo que guardara el primer nombre del Empleado.',
  `nombreEmpleado2` VARCHAR(15) NULL DEFAULT NULL COMMENT 'Campo que guardara el segundo nombre del Empleado.',
  `apellidoEmpleado` VARCHAR(15) NOT NULL COMMENT 'Campo que guardara el primer apellido del Empleado.',
  `apellidoEmpleado2` VARCHAR(15) NULL DEFAULT NULL COMMENT 'Campo que guardara el segundo apellido del Empleado.',
  `cedulaEmpleado` BIGINT(12) NOT NULL COMMENT 'Campo que guardara la cédula del Empleado.',
  `telefonoEmpleado` BIGINT(8) NOT NULL COMMENT 'Campo que guardara el numero de telefono del Empleado',
  `celularEmpleado` BIGINT(10) NULL DEFAULT NULL COMMENT 'Campo que guardara el numero de celular del Empleado.',
  `fechaNacimientoEmpleado` DATE NOT NULL COMMENT 'Campo que guardará la fecha de nacimiento del Empleado.',
  `direccionEmpleado` VARCHAR(30) NOT NULL COMMENT 'Campo que guardara la dirección del Empleado.',
  PRIMARY KEY (`idEmpleado`),
  INDEX `fk_Empleado_Regional1_idx` (`idRegional` ASC),
  INDEX `fk_Empleado_Departamento1_idx` (`idDepartamento` ASC),
  INDEX `fk_Empleado_Ciudad1_idx` (`idCiudad` ASC),
  INDEX `fk_Empleado_Barrio1_idx` (`idBarrio` ASC),
  INDEX `fk_Empleado_Rol1_idx` (`idRol` ASC),
  INDEX `fk_Empleado_Genero1_idx` (`idGenero` ASC),
  CONSTRAINT `fk_Empleado_Regional1`
    FOREIGN KEY (`idRegional`)
    REFERENCES `coomeva`.`Regional` (`idRegional`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Departamento1`
    FOREIGN KEY (`idDepartamento`)
    REFERENCES `coomeva`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Ciudad1`
    FOREIGN KEY (`idCiudad`)
    REFERENCES `coomeva`.`Ciudad` (`idCiudad`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Barrio1`
    FOREIGN KEY (`idBarrio`)
    REFERENCES `coomeva`.`Barrio` (`idBarrio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Rol1`
    FOREIGN KEY (`idRol`)
    REFERENCES `coomeva`.`Rol` (`idRol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Genero1`
    FOREIGN KEY (`idGenero`)
    REFERENCES `coomeva`.`Genero` (`idGenero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará la información del Empleado.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Flujo_Visita` (
  `idFlujo_Visita` BIGINT(15) NOT NULL COMMENT 'Campo que guardará el Código Visita.',
  `numeroVisitas` BIGINT(10) NOT NULL COMMENT 'Campo que guardará el número de visitas del Empleado.',
  `fechaVisita` DATE NOT NULL COMMENT 'Campo que guardará la fecha de la vista del Empleado.',
  PRIMARY KEY (`idFlujo_Visita`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará el flujo de las visitas al aplicativo del Empleado.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Permiso` (
  `idPermiso` INT(3) NOT NULL COMMENT 'Campo que guardará el Código del Permiso.',
  `nombrePermiso` VARCHAR(20) NOT NULL COMMENT 'Campo que guardara el nombre del Permiso.',
  PRIMARY KEY (`idPermiso`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará los permisos de los roles.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Rol` (
  `idRol` INT(3) NOT NULL COMMENT 'Campo que guardará el Código del Rol.',
  `nombreRol` VARCHAR(20) NOT NULL COMMENT 'Campo que guardara el nombre del Rol.',
  PRIMARY KEY (`idRol`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que guardará el Rol del Empleado.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Permiso_has_Rol` (
  `idPermiso` INT(3) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Permiso.',
  `idRol` INT(3) NOT NULL COMMENT 'Campo que guardara la llave foránea de la tabla Rol.',
  PRIMARY KEY (`idPermiso`, `idRol`),
  INDEX `fk_Permiso_has_Rol_Rol1_idx` (`idRol` ASC),
  INDEX `fk_Permiso_has_Rol_Permiso1_idx` (`idPermiso` ASC),
  CONSTRAINT `fk_Permiso_has_Rol_Permiso1`
    FOREIGN KEY (`idPermiso`)
    REFERENCES `coomeva`.`Permiso` (`idPermiso`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Permiso_has_Rol_Rol1`
    FOREIGN KEY (`idRol`)
    REFERENCES `coomeva`.`Rol` (`idRol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que detalla la tabla Permiso y Rol.';

CREATE TABLE IF NOT EXISTS `coomeva`.`Empleado_has_Flujo_Visita` (
  `idEmpleado` BIGINT(15) NOT NULL COMMENT 'Campo que guarda la llave foránea de la tabla Empleado.',
  `idFlujo_Visita` BIGINT(15) NOT NULL COMMENT 'Campo que guarda la llave foránea de la tabla Flujo Visita.',
  PRIMARY KEY (`idEmpleado`, `idFlujo_Visita`),
  INDEX `fk_Empleado_has_Flujo_Visita_Flujo_Visita1_idx` (`idFlujo_Visita` ASC),
  INDEX `fk_Empleado_has_Flujo_Visita_Empleado1_idx` (`idEmpleado` ASC),
  CONSTRAINT `fk_Empleado_has_Flujo_Visita_Empleado1`
    FOREIGN KEY (`idEmpleado`)
    REFERENCES `coomeva`.`Empleado` (`idEmpleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_has_Flujo_Visita_Flujo_Visita1`
    FOREIGN KEY (`idFlujo_Visita`)
    REFERENCES `coomeva`.`Flujo_Visita` (`idFlujo_Visita`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1
COLLATE = latin1_spanish_ci
COMMENT = 'Tabla que detalla la tabla Empleado y Flujo Visita.';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
