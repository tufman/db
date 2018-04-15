﻿--
-- Script was generated by Devart dbForge Studio for MySQL, Version 7.2.78.0
-- Product home page: http://www.devart.com/dbforge/mysql/studio
-- Script date 4/15/2018 3:07:03 PM
-- Server version: 5.5.5-10.1.20-MariaDB
-- Client version: 4.1
--


-- 
-- Disable foreign keys
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Set SQL mode
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

-- 
-- Set default database
--
USE coursedb;

--
-- Definition for table adress
--
DROP TABLE IF EXISTS adress;
CREATE TABLE adress (
  adress VARCHAR(255) DEFAULT NULL,
  id INT(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 5
AVG_ROW_LENGTH = 4096
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table chain
--
DROP TABLE IF EXISTS chain;
CREATE TABLE chain (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) DEFAULT NULL,
  description VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 16
AVG_ROW_LENGTH = 3276
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table mall_group
--
DROP TABLE IF EXISTS mall_group;
CREATE TABLE mall_group (
  id INT(11) NOT NULL AUTO_INCREMENT,
  group_name VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 4
AVG_ROW_LENGTH = 5461
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table role
--
DROP TABLE IF EXISTS role;
CREATE TABLE role (
  id INT(11) NOT NULL AUTO_INCREMENT,
  description VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 8192
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table mall
--
DROP TABLE IF EXISTS mall;
CREATE TABLE mall (
  id INT(11) NOT NULL AUTO_INCREMENT,
  mall_adress INT(11) DEFAULT NULL,
  mall_group INT(11) DEFAULT NULL,
  PRIMARY KEY (id),
  INDEX FK_mall_mall_groups_id (mall_group),
  CONSTRAINT FK_mall_adress_id FOREIGN KEY (mall_adress)
    REFERENCES adress(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AUTO_INCREMENT = 5
AVG_ROW_LENGTH = 4096
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table store
--
DROP TABLE IF EXISTS store;
CREATE TABLE store (
  id INT(11) NOT NULL AUTO_INCREMENT,
  name VARCHAR(50) DEFAULT NULL,
  adress INT(11) DEFAULT NULL,
  chain_id INT(11) DEFAULT NULL,
  store_num INT(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_store_adress_id FOREIGN KEY (adress)
    REFERENCES adress(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_store_chains_id FOREIGN KEY (chain_id)
    REFERENCES chain(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AUTO_INCREMENT = 10
AVG_ROW_LENGTH = 5461
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table employee
--
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
  id INT(11) NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) DEFAULT NULL,
  role INT(11) DEFAULT NULL,
  chain_id INT(11) DEFAULT NULL,
  store_id INT(11) DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_employee_chain_id FOREIGN KEY (chain_id)
    REFERENCES chain(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_employee_roles_id FOREIGN KEY (role)
    REFERENCES role(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_employee_store_id FOREIGN KEY (store_id)
    REFERENCES store(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AUTO_INCREMENT = 5
AVG_ROW_LENGTH = 4096
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table mall2group
--
DROP TABLE IF EXISTS mall2group;
CREATE TABLE mall2group (
  mall_group_id INT(11) DEFAULT NULL,
  mall_id INT(11) DEFAULT NULL,
  CONSTRAINT FK_mall2group_mall_group_id FOREIGN KEY (mall_group_id)
    REFERENCES mall_group(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_mall2group_mall_id FOREIGN KEY (mall_id)
    REFERENCES mall(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
AVG_ROW_LENGTH = 4096
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Definition for table mall2store
--
DROP TABLE IF EXISTS mall2store;
CREATE TABLE mall2store (
  store_id INT(11) DEFAULT NULL,
  mall_id INT(11) DEFAULT NULL,
  INDEX FK_mall2store_mall2group_store_id (store_id),
  CONSTRAINT FK_mall2store_mall_id FOREIGN KEY (mall_id)
    REFERENCES mall(id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT FK_mall2store_store_id FOREIGN KEY (store_id)
    REFERENCES store(id) ON DELETE NO ACTION ON UPDATE NO ACTION
)
ENGINE = INNODB
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

-- 
-- Dumping data for table adress
--
INSERT INTO adress VALUES
('Rehovot ', 1),
('Tel Aviv', 2),
('NY', 3),
('Dalas', 4);

-- 
-- Dumping data for table chain
--
INSERT INTO chain VALUES
(1, 'mytest', NULL),
(2, 'koala bear', 'chain of stores for baby clothes'),
(3, 'work on it', 'chain of office supplies stores'),
(4, 'Casual wear', 'chain of stores for family clothes'),
(15, 'ooooo', NULL);

-- 
-- Dumping data for table mall_group
--
INSERT INTO mall_group VALUES
(1, 'Azriali'),
(2, 'Ofer'),
(3, 'Big');

-- 
-- Dumping data for table role
--
INSERT INTO role VALUES
(1, 'managment'),
(2, 'employee');

-- 
-- Dumping data for table mall
--
INSERT INTO mall VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 2),
(4, 4, 2);

-- 
-- Dumping data for table store
--
INSERT INTO store VALUES
(7, 'oded1', 1, 1, 1),
(8, 'ddd', 1, 1, 1),
(9, 'testStore', 1, 1, 12);

-- 
-- Dumping data for table employee
--
INSERT INTO employee VALUES
(1, 'Oded', 'Kessler', 2, 1, NULL),
(2, 'Shay', 'Tufman', 1, 2, NULL),
(3, 'Yelena', 'K', 1, 3, NULL),
(4, 'Kirsh1', 'Amir1', 1, 1, NULL);

-- 
-- Dumping data for table mall2group
--
INSERT INTO mall2group VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 4);

-- 
-- Dumping data for table mall2store
--

-- Table coursedb.mall2store does not contain any data (it is empty)

-- 
-- Restore previous SQL mode
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Enable foreign keys
-- 
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;