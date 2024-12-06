##1. Kategori: Varje kategori ska ha ett namn, en symbol ( )
##och en beskrivning.
CREATE TABLE IF NOT EXISTS category (
    id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL ,
    name VARCHAR(255) UNIQUE NOT NULL,
    symbol VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS location (
    id INTEGER AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL ,
    user VARCHAR(255) UNIQUE,
    category_id INTEGER,
    description VARCHAR(255),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_public BOOLEAN DEFAULT TRUE,
    coordinate GEOMETRY NOT NULL SRID 4326,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (category_id) REFERENCES category(id)




);




