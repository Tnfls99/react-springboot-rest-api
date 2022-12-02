use shop;
CREATE TABLE product
(
    id              INTEGER unsigned PRIMARY KEY auto_increment,
    name            varchar(30),
    price           INTEGER,
    category        varchar(20),
    color           varchar(30),
    stock           INTEGER,
    registered_at   datetime(6) DEFAULT CURRENT_TIMESTAMP(6),
    image_url       text,
    is_made         BOOLEAN
);
