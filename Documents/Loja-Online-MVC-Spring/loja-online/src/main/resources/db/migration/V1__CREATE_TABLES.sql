CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role TEXT NOT NULL
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description VARCHAR(255) NOT NULL,
    rating BIGINT
    
);

CREATE TABLE cart (
    id SERIAL PRIMARY KEY
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY
);
CREATE TABLE cart_item(
    realId BIGINT PRIMARY KEY NOT NULL,
    id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    description VARCHAR(255) NOT NULL,
    rating BIGINT,
    quantity INT
   );
