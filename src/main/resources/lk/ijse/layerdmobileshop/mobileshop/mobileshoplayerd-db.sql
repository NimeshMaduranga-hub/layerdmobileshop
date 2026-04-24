
create database mobileshoplayerd;

use mobileshoplayerd;

DROP TABLE IF EXISTS `Customer`;

CREATE TABLE Customer (
                          id VARCHAR(30) NOT NULL,
                          name VARCHAR(40),
                          address VARCHAR(100),
                          mobile VARCHAR(100),

                          PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `Item`;

CREATE TABLE Item (
                      code VARCHAR(50) NOT NULL,
                      description VARCHAR(255),
                      receivedDate DATE,
                      qtyOnHand INT,
                      unitPrice DECIMAL(10,2),
                      storage VARCHAR(50),
                      color VARCHAR(50),
                      emiNo VARCHAR(50),
                      warranty VARCHAR(100),

                      PRIMARY KEY (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `Orders`;


CREATE TABLE Orders (
                        oid VARCHAR(50) NOT NULL,
                        date DATE,
                        customerID VARCHAR(30),
                        orderTotal DECIMAL(10,2) DEFAULT 0.00,

                        PRIMARY KEY (oid),

                        CONSTRAINT fk_customer
                            FOREIGN KEY (customerID)
                                REFERENCES Customer(id)
                                ON DELETE CASCADE
                                ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `OrderDetails`;

CREATE TABLE OrderDetails (
                              oid VARCHAR(50) NOT NULL,
                              itemCode VARCHAR(50) NOT NULL,
                              description VARCHAR(255),
                              qty INT,
                              unitPrice DECIMAL(10,2),
                              storage VARCHAR(50),
                              color VARCHAR(50),
                              emiNo VARCHAR(50),
                              warranty VARCHAR(50),
                              total DECIMAL(10,2),

                              PRIMARY KEY (oid, itemCode),

                              CONSTRAINT fk_order
                                  FOREIGN KEY (oid)
                                      REFERENCES Orders(oid)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE,

                              CONSTRAINT fk_item
                                  FOREIGN KEY (itemCode)
                                      REFERENCES Item(code)
                                      ON DELETE CASCADE
                                      ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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