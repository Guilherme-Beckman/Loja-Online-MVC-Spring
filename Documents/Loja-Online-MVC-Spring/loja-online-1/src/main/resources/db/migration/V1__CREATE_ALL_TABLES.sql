CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    user_id BIGINT NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE cart (
    id SERIAL PRIMARY KEY
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id BIGINT
);

CREATE TABLE cartitem (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    quantity INT NOT NULL,
    description VARCHAR(255) NOT NULL
);
