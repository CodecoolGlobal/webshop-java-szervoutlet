DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cart;

CREATE TABLE products
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    default_price FLOAT,
    currency VARCHAR(3) NOT NULL,
    description VARCHAR(250),
    supplier INTEGER,
    product_category INTEGER
);

CREATE TABLE supplier
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    description VARCHAR(125)
);

CREATE TABLE product_category
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50),
    department VARCHAR(25),
    description VARCHAR(125)
);

CREATE TABLE users
(
    id SERIAL PRIMARY KEY NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    phone_number INTEGER,
    billing_address TEXT,
    shipping_address TEXT
);

CREATE TABLE cart
(
    id SERIAL PRIMARY KEY NOT NULL,
    product_id INTEGER,
    user_id INTEGER NOT NULL
);


ALTER TABLE ONLY products
    ADD CONSTRAINT fk_supplier FOREIGN KEY (supplier) REFERENCES supplier(id);

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_product_category FOREIGN KEY (product_category) REFERENCES product_category(id);

ALTER TABLE ONLY cart
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE ONLY cart
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);
