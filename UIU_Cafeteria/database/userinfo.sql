CREATE DATABASE uiu_cafeteria;
USE uiu_cafeteria;
CREATE TABLE user_info(
username VARCHAR(50),
mobile VARCHAR(50),
email VARCHAR(50),
password VARCHAR(50),
birthdate VARCHAR(50));

SELECT * FROM user_info; 
UPDATE user_info
SET password = 'new_password'
WHERE email = 'specific_username';