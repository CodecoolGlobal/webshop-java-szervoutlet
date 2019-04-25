DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_category;
DROP TABLE IF EXISTS supplier;

create schema public;

comment on schema public is 'standard public schema';

alter schema public owner to postgres;

create table if not exists supplier
(
    id          serial not null
        constraint supplier_pkey
            primary key,
    name        varchar(50),
    description varchar(125)
);

alter table supplier
    owner to postgres;

create table if not exists product_category
(
    id          serial not null
        constraint product_category_pkey
            primary key,
    name        varchar(50),
    department  varchar(25),
    description varchar(125)
);

alter table product_category
    owner to postgres;

create table if not exists products
(
    id               serial                                      not null
        constraint products_pkey
            primary key,
    name             varchar(50),
    default_price    double precision,
    currency         varchar(3) default 'USD'::character varying not null,
    description      varchar(250),
    supplier         integer    default 1
        constraint fk_supplier
            references supplier,
    product_category integer    default 1
        constraint fk_product_category
            references product_category,
    quantity         integer    default 0
);

alter table products
    owner to postgres;

create table if not exists users
(
    id               serial not null
        constraint users_pkey
            primary key,
    email            text   not null
        constraint users_email_key
            unique,
    password         text   not null,
    phone_number     integer,
    billing_address  text,
    shipping_address text
);

alter table users
    owner to postgres;



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