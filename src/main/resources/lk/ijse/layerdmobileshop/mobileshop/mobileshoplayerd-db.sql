
create database mobileshoplayerd;

use mobileshoplayerd;

DROP TABLE IF EXISTS `Customer`;

CREATE TABLE `Customer` (
                            `id` varchar(30) NOT NULL,
                            `name` varchar(40) DEFAULT NULL,
                            `address` varchar(100) DEFAULT NULL,
                            `mobile` varchar(100) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Item`;

CREATE TABLE `Item` (
                        `code` varchar(255) NOT NULL,
                        `description` varchar(255) DEFAULT NULL,
                        `qtyOnHand` int(10) DEFAULT NULL,
                        `unitPrice` decimal(10,2) DEFAULT NULL,
                        PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Orders`;


CREATE TABLE `Orders` (
                          `oid` varchar(255) NOT NULL,
                          `date` date DEFAULT NULL,
                          `customerID` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`oid`),
                          KEY `customerID` (`customerID`),
                          CONSTRAINT `Orders_ibfk_1` FOREIGN KEY (`customerID`) REFERENCES `Customer` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `OrderDetails`;

CREATE TABLE `OrderDetails` (
                                `oid` varchar(255) NOT NULL,
                                `itemCode` varchar(255) NOT NULL,
                                `qty` int(10) DEFAULT NULL,
                                `unitPrice` decimal(10,2) DEFAULT NULL,
                                PRIMARY KEY (`oid`,`itemCode`),
                                KEY `itemCode` (`itemCode`),
                                CONSTRAINT `OrderDetails_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `Orders` (`oid`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `OrderDetails_ibfk_2` FOREIGN KEY (`itemCode`) REFERENCES `Item` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
                        `username` VARCHAR(50) NOT NULL,
                        `password` VARCHAR(100) NOT NULL,
                        `role` VARCHAR(20) NOT NULL,
                        PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Employee`;

CREATE TABLE `Employee` (
                            `id` varchar(30) NOT NULL,
                            `name` varchar(100) DEFAULT NULL,
                            `address` varchar(150) DEFAULT NULL,
                            `mobile` varchar(20) DEFAULT NULL,
                            `salary` decimal(10,2) DEFAULT NULL,
                            `role` varchar(20) DEFAULT 'CASHIER',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Attendance`;
CREATE TABLE `Attendance` (
                              `id` INT AUTO_INCREMENT PRIMARY KEY,
                              `employeeid` VARCHAR(30),
                              `date` DATE,
                              `checkin` TIME,
                              `checkout` TIME,
                              `status` ENUM('Present','Absent','Half Day','Leave'),
                              FOREIGN KEY (`employeeid`) REFERENCES `Employee`(`id`)
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `Salary`;

CREATE TABLE `Salary` (
                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                          `employee_id` VARCHAR(30) NOT NULL,
                          `salary` DECIMAL(10,2) NOT NULL,
                          `pay_date` DATE NOT NULL,
                          FOREIGN KEY (`employee_id`) REFERENCES `Employee`(`id`)
                              ON DELETE CASCADE
                              ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;