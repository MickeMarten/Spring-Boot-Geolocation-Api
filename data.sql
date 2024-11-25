TRUNCATE TABLE location;
TRUNCATE TABLE category;

INSERT INTO category (name, symbol, description)
VALUES
    ('Park', '🌳', 'A public park or recreational area'),
    ('Museum', '🏛️', 'A place of historical or cultural interest'),
    ('Restaurant', '🍴', 'Places to eat in the city');


INSERT INTO location (name, category_id, description, coordinate)
VALUES
    ('Strömparken', 1, 'A park along the Motala river with walking paths', ST_GeomFromText('POINT(16.1872 58.5937)', 4326)),
    ('De Geerhallen', 2, 'An exhibition hall and event space in Norrköping', ST_GeomFromText('POINT(16.1854 58.5939)', 4326)),
    ('Kolmårdens Djurpark', 1, 'A zoo and wildlife park just outside Norrköping', ST_GeomFromText('POINT(16.6295 58.8065)', 4326)),
    ('Södra Stadsparken', 1, 'A large park with playgrounds and walking trails', ST_GeomFromText('POINT(16.1760 58.5914)', 4326)),
    ('Sankt Olai Kyrka', 2, 'A historic church in the city center', ST_GeomFromText('POINT(16.1875 58.5912)', 4326)),
    ('Himmelstalund', 1, 'A recreational area with sports facilities and trails', ST_GeomFromText('POINT(16.2431 58.6107)', 4326)),
    ('Pizzeria Napoli', 3, 'Authentic Italian pizza in central Norrköping', ST_GeomFromText('POINT(16.1890 58.5921)', 4326));


DELETE FROM category
WHERE id = 6;

DELETE FROM location
WHERE id = 1 ;

UPDATE location SET is_public = 0 WHERE id = 5;


SELECT id, ST_AsText(coordinate) FROM location;

SELECT id, ST_SRID(coordinate) FROM location;

SELECT id, ST_IsValid(coordinate) FROM location;

UPDATE location
SET coordinate = ST_SRID(coordinate, 4326)
WHERE ST_SRID(coordinate) != 4326;

SELECT *
FROM location l1
WHERE ST_Distance(ST_SRID(l1.coordinate, 4326), ST_SRID(? , 4326)) <= ?;

