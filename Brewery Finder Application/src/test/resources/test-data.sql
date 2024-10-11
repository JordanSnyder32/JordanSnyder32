BEGIN TRANSACTION;

-- Users
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user1','user1','ROLE_USER', 'User1 Name', 'User1 address', 'Cleveland', 'OH', '44123');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user2','user2','ROLE_USER', 'User2 Name', null, 'Beverly Hills', 'CA', '90210');
INSERT INTO users (username,password_hash,role, name, address, city, state_code, zip) VALUES ('user3','user3','ROLE_USER', 'User3 Name', 'User3 address', 'Chicago', 'IL', '60609');

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
	(2, 2, 5, 'Land Grant just doesnt miss, especially with Beard Crumbs! One of my favorite stouts!', '2024-08-27'),
	(2, 3, 1, 'Just doesnt compare to the IPAs found in columbus.', '2024-02-02');

INSERT INTO user_brewery (user_id, brewery_id) VALUES
	(1, 2);


COMMIT TRANSACTION;
