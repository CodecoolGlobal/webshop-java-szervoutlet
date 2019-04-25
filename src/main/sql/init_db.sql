DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS supplier;

CREATE TABLE products
(
    id               SERIAL PRIMARY KEY       NOT NULL,
    name             VARCHAR(50),
    default_price    FLOAT,
    currency         VARCHAR(3) DEFAULT 'USD' NOT NULL,
    description      VARCHAR(250),
    supplier         INTEGER    DEFAULT 1,
    product_category INTEGER    DEFAULT 1
);

CREATE TABLE supplier
(
    id          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(50),
    description VARCHAR(125)
);

CREATE TABLE product_category
(
    id          SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(50),
    department  VARCHAR(25),
    description VARCHAR(125)
);

CREATE TABLE users
(
    id               SERIAL PRIMARY KEY NOT NULL,
    email            TEXT               NOT NULL UNIQUE,
    password         TEXT               NOT NULL,
    phone_number     INTEGER,
    billing_address  TEXT,
    shipping_address TEXT
);

CREATE TABLE cart
(
    id         SERIAL PRIMARY KEY NOT NULL,
    product_id INTEGER,
    user_id    INTEGER            NOT NULL,
    quantity   INTEGER
);


ALTER TABLE ONLY products
    ADD CONSTRAINT fk_supplier FOREIGN KEY (supplier) REFERENCES supplier (id);

ALTER TABLE ONLY products
    ADD CONSTRAINT fk_product_category FOREIGN KEY (product_category) REFERENCES product_category (id);

ALTER TABLE ONLY cart
    ADD CONSTRAINT fk_product_id FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE ONLY cart
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id);


INSERT INTO product_category(name, department, description)
VALUES ('Motoric organ', 'Bionic', 'Arms, legs and other connections keep your body move'),
       ('Body organs', 'Bionic', 'Lung, Liver, and all the stuffs located in the heart of Human Body'),
       ('Head', 'Bionic', 'Brains, eyes, tounge and everything located  in your, our in your mates head');

INSERT INTO supplier (name, description)
VALUES ('Burkina Faso', 'From the heart of the West Africa'),
       ('South Senegal', 'From the heart of the South Africa'),
       ('Mozambic', 'From the heart of Mozambic'),
       ('Tibet', 'Asian high quality from the goats'),
       ('Marocco', 'Asian high quality from the goats'),
       ('West Sudan', 'Asian high quality from the goats');

INSERT INTO products (name, default_price, description, supplier, product_category)
VALUES ('Stock Brain', 1000, 'Traditional central processing unit of a Human body', 5, 3),
       ('Almost new Liver', 749.99, 'Freshly imported liver', 1, 2),
       ('Kidney', 399.99, '50% OFF', 2, 3),
       ('Bionic heart', 1900, 'Send a heart for your friend now in real life', 6, 2),
       ('Left pitvar', 189.99, 'Left pitvar for everyone! Fresh', 4, 2),
       ('Eyes', 469.75, 'Behind blue eyes! - The Who!!!', 1, 3),
       ('Ear', 439.99,
        'Your grandma didnt hear that you make embarrassing comments for his shape? Just buy an ear and wait for the big lovely slap',
        4, 3),
       ('Remanufactured Ear', 170, 'Buy this ear for your grandma!! Laugh on him with your family', 4, 2),
       ('Bloody Eye', 259.99, 'Eyes for screaming all man on the metro?? Theres your product!! Now 10% sale', 2, 3),
       ('Stressed heart', 920, 'Used heart for sale', 5, 2),
       ('Half ready Lungs', 920, 'Half fresh, half dead lung for the brave men', 4, 2),
       ('calculus(veseko)', 69.99, 'A real jewellery for your partner', 4, 2),
       ('Small Brain', 169.99, 'Brain for people who refusing the fact that the planet is flat', 3, 3),
       ('Stomach', 999.99, 'If you dont want to spend a lot of money for food', 2, 2),
       ('Kidney with Iphone', 299.99, 'A real jewellery for your partner', 3, 2);