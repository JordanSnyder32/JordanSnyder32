-- database Brewery
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS user_brewery;
DROP TABLE IF EXISTS reviews;   
DROP TABLE IF EXISTS beers;      
DROP TABLE IF EXISTS users;      
DROP TABLE IF EXISTS beer_types; 
DROP TABLE IF EXISTS breweries;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

-- beer_types
CREATE TABLE beer_types (
	beer_type_id SERIAL PRIMARY KEY,
	beer_type varchar(30) NOT NULL UNIQUE
);

-- breweries
CREATE TABLE breweries (
	brewery_id SERIAL PRIMARY KEY,
	name varchar(50) NOT NULL UNIQUE,
	city varchar(35) NOT NULL,
	state_code varchar(2) NOT NULL,
	description TEXT
); 

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	name varchar(50) NOT NULL,
	address varchar(100) NULL,
	city varchar(50) NULL,
	state_code char(2) NULL,
	zip varchar(5) NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- beers
CREATE TABLE beers (
	beer_id SERIAL PRIMARY KEY,
	brewery_id INT REFERENCES breweries(brewery_id),
	name varchar(50) NOT NULL,
	beer_type_id INT REFERENCES beer_types(beer_type_id),
	CONSTRAINT unique_brewery_beer UNIQUE (brewery_id, name)
);

-- reviews
CREATE TABLE reviews (
	review_id SERIAL PRIMARY KEY,
	user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
	beer_id INT REFERENCES beers(beer_id) ON DELETE CASCADE,
	rating INT CHECK (rating >= 1 AND rating <= 5),
	review TEXT,
	review_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE user_brewery (
    user_brewery_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id) ON DELETE CASCADE,
    brewery_id INT REFERENCES breweries(brewery_id) ON DELETE CASCADE
);



-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************

-- Users
-- Password for all users is password
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES 
    ('user', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_USER',  'Jack O''Lantern', null, 'Cleveland', 'OH', '44123'),
    ('admin','$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem','ROLE_ADMIN', 'Jill O''Lantern', null, 'Beverly Hills', 'CA', '90210');

-- Breweries
INSERT INTO breweries (name, city, state_code, description) VALUES 
    ('Land Grant', 'Columbus',  'OH', 'We have tons of patio space and food trucks!'),
    ('Rhinegeist Brewery', 'Cincinnati', 'OH', 'Our beers can be found all over the tristate area!'),
    ('Columbus Brewing Company', 'Columbus', 'OH', ''),
    ('Nocterra Brewing Co.', 'Columbus', 'OH', '');
   
-- Beer Types
INSERT INTO beer_types(beer_type) VALUES
    ('IPA'),
    ('Stout'),
    ('Lager'),
	('Cider'),
	('Amber ale');

-- Beers
INSERT INTO beers (brewery_id, name, beer_type_id) VALUES 
	(4, 'The Dan', 3),
	(1, 'Beard Crumbs', 2),
	(2, 'Truth', 1);
	
-- Reviews
INSERT INTO reviews (user_id, beer_id, rating, review, review_date) VALUES
	(1, 3, 5, 'One of my favorite IPAs from cincy!', '2024-01-02'),
	(2, 2, 5, 'Land Grant just doesnt miss, especially with Beard Crumbs! One of my favorite stouts!', '2024-08-27');
	(2, 3, 1, 'Just doesnt compare to the IPAs found in columbus.', '2024-02-02')

INSERT INTO user_brewery (user_id, brewery_id) VALUES
	(1, 2);

COMMIT TRANSACTION;
