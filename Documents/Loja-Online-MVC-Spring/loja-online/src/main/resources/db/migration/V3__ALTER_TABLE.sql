
ALTER TABLE product
  ALTER COLUMN user_id SET NOT NULL;
  
ALTER TABLE cart
  ALTER COLUMN user_id SET NOT NULL;
  
ALTER TABLE users
  ALTER COLUMN cart_id SET NOT NULL;
