DROP TABLE IF EXISTS product;
CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price DOUBLE NOT NULL
);
DROP TABLE IF EXISTS technology;
CREATE TABLE technology (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            description VARCHAR(500) NOT NULL
);
