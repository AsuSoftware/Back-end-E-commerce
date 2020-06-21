CREATE TABLE image(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    image_product TEXT NOT NULL
);

CREATE TABLE ad(
id BIGSERIAL NOT NULL PRIMARY KEY,
title VARCHAR(20) NOT NULL,
description_product TEXT NOT NULL,
price VARCHAR(10) NOT NULL,
category VARCHAR(20) NOT NULL,
image_id BIGINT REFERENCES image (id),                                               
UNIQUE(image_id)
);

CREATE TABLE users( 
id BIGSERIAL NOT NULL PRIMARY KEY,  
first_name VARCHAR(50) NOT NULL,    
last_name VARCHAR(50) NOT NULL,
gender VARCHAR(10) NOT NULL,      
user_birthday DATE NOT NULL,   
email VARCHAR(50) NOT NULL,    
password VARCHAR(30) NOT NULL,                 
ad_id BIGINT REFERENCES ad (id),                                               
UNIQUE(ad_id)                                                                  
);