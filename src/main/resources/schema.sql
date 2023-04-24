CREATE TABLE if NOT EXISTS phone
(
    id             integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    phone_name      varchar(255),
    phone_style     varchar (255),
    imei            varchar (25),
    quantity_on_hand integer,
    price          decimal,
    created_date   timestamp,
    last_modified_date timestamp
);

CREATE TABLE if NOT EXISTS customer
(
    id                 integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    customer_name      varchar(255),
    created_date       timestamp,
    last_modified_date timestamp
);