TRUNCATE TABLE location;
TRUNCATE TABLE category;

INSERT INTO category (name, symbol, description)
VALUES
    ('Park', '🌳', 'A public park or recreational area'),
    ('Museum', '🏛️', 'A place of historical or cultural interest'),
    ('Restaurant', '🍴', 'Places to eat in the city');


INSERT INTO location (name, category_id, description, coordinate)
VALUES
    ('Folkparken', 1, 'A beautiful park in Norrköping', ST_GeomFromText('POINT(16.1887 58.5906)', 4326)),
    ('Arbetets museum', 2, 'Museum of work and industry', ST_GeomFromText('POINT(16.1925 58.5894)', 4326)),
    ('Restaurant Enoteket', 3, 'Italian-inspired restaurant in the city center', ST_GeomFromText('POINT(16.2002 58.5919)', 4326));


DELETE FROM category
WHERE id = 6;