DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS supplier;


CREATE TABLE supplier
(
    name        VARCHAR(50) PRIMARY KEY NOT NULL,
    description VARCHAR(125),
    shortname   VARCHAR(20) UNIQUE

);

CREATE TABLE product_category
(
    name        VARCHAR(50) PRIMARY KEY NOT NULL,
    department  VARCHAR(25),
    description VARCHAR(125),
    type        VARCHAR(20) UNIQUE
);

CREATE TABLE products
(
    id            SERIAL PRIMARY KEY       NOT NULL,
    name          VARCHAR(50),
    default_price FLOAT,
    currency      VARCHAR(3) DEFAULT 'USD' NOT NULL,
    description   VARCHAR(250),
    supp          VARCHAR(20),
    prod_cat      VARCHAR(20),
    FOREIGN KEY (supp) REFERENCES supplier (shortname),
    FOREIGN KEY (prod_cat) REFERENCES product_category (type)
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
    user_id    INTEGER            NOT NULL
);

INSERT INTO product_category(name, department, description, type)
VALUES ('Motoric organ', 'Bionic', 'Arms, legs and other connections keep your body move', 'motoric'),
       ('Body organs', 'Bionic', 'Lung, Liver, and all the stuffs located in the heart of Human Body', 'body'),
       ('Head', 'Bionic', 'Brains, eyes, tounge and everything located  in your, our in your mates head', 'head');

INSERT INTO supplier (name, description, shortname)
VALUES ('Burkina Faso', 'From the heart of the West Africa', 'burkina'),
       ('South Senegal', 'From the heart of the South Africa', 'senegal'),
       ('Mozambic', 'From the heart of Mozambic', 'mozambic'),
       ('Tibet', 'Asian high quality from the goats', 'tibet'),
       ('Marocco', 'Asian high quality from the goats', 'marocco'),
       ('West Sudan', 'Asian high quality from the goats', 'sudan');

INSERT INTO products (name, default_price, description, prod_cat, supp)
VALUES ('Stock Brain', 1000, 'Traditional central processing unit of a Human body', 'head', 'marocco'),
       ('Almost new Liver', 749.99, 'Freshly imported liver', 'body', 'burkina'),
       ('Kidney', 399.99, '50% OFF', 'body', 'burkina'),
       ('Bionic heart', 1900, 'Send a heart for your friend now in real life', 'motoric', 'mozambic'),
       ('Left pitvar', 189.99, 'Left pitvar for everyone! Fresh', 'body', 'senegal'),
       ('Eyes', 469.75, 'Behind blue eyes! - The Who!!!', 'head', 'tibet'),
       ('Ear', 439.99,
        'Your grandma didnt hear that you make embarrassing comments for his shape? Just buy an ear and wait for the big lovely slap',
        'head', 'mozambic'),
       ('Remanufactured Ear', 170, 'Buy this ear for your grandma!! Laugh on him with your family', 'head', 'senegal'),
       ('Bloody Eye', 259.99, 'Eyes for screaming all man on the metro?? Theres your product!! Now 10% sale', 'head',
        'sudan'),
       ('Stressed heart', 920, 'Used heart for sale', 'body', 'sudan'),
       ('Half ready Lungs', 920, 'Half fresh, half dead lung for the brave men', 'body', 'tibet'),
       ('calculus(veseko)', 69.99, 'A real jewellery for your partner', 'body', 'marocco'),
       ('Small Brain', 169.99, 'Brain for people who refusing the fact that the planet is flat', 'head', 'sudan'),
       ('Stomach', 999.99, 'If you dont want to spend a lot of money for food', 'body', 'marocco'),
       ('Kidney with Iphone', 299.99, 'A real jewellery for your partner', 'body', 'senegal');