use shop;
drop table customer;
drop table order_items;
drop table orders;
drop table product;
CREATE TABLE customer
(
    id              INTEGER unsigned PRIMARY KEY auto_increment,
    name            varchar(30) NOT NULL,
    email           varchar(50) NOT NULL,
    address         text,
    CONSTRAINT UNIQUE (email)
);

CREATE TABLE product
(
    id              INTEGER unsigned PRIMARY KEY auto_increment,
    name            text,
    price           INTEGER,
    category        varchar(20),
    color           varchar(30),
    stock           INTEGER,
    image_url       text,
    is_made         BOOLEAN
);

CREATE TABLE orders
(
    id           INTEGER unsigned PRIMARY KEY auto_increment,
    email        VARCHAR(50)  NOT NULL,
    address      text NOT NULL,
    order_status VARCHAR(50)  NOT NULL
);

CREATE TABLE order_items
(
    id         INTEGER   unsigned  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id   INTEGER   unsigned   NOT NULL,
    product_id INTEGER  unsigned    NOT NULL,
    price      INTEGER      NOT NULL,
    quantity   INTEGER      NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE cart
(
    id INTEGER unsigned not null PRIMARY KEY AUTO_INCREMENT,
    customer_id INTEGER unsigned not null,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE cart_items
(
    id INTEGER unsigned not null PRIMARY KEY AUTO_INCREMENT,
    cart_id INTEGER unsigned not null ,
    product_id INTEGER unsigned not null ,
    quantity INTEGER unsigned not null ,
    FOREIGN KEY (cart_id) REFERENCES cart (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id)
);
