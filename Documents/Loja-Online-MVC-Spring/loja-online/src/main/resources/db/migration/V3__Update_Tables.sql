
ALTER TABLE users
ADD COLUMN cart_id BIGINT NOT NULL,
ADD COLUMN product_id BIGINT,
ADD CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id),
ADD CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES cart(id);


ALTER TABLE product
ADD COLUMN user_id BIGINT NOT NULL,
ADD COLUMN cart_id BIGINT,
ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
ADD CONSTRAINT fk_cart_product FOREIGN KEY (cart_id) REFERENCES cart(id);


ALTER TABLE cart
ADD COLUMN user_id BIGINT NOT NULL,
ADD COLUMN product_id BIGINT,
ADD CONSTRAINT fk_user_cart FOREIGN KEY (user_id) REFERENCES users(id),
ADD CONSTRAINT fk_product_cart FOREIGN KEY (product_id) REFERENCES product(id);

