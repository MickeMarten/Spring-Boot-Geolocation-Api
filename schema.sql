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







##    2. Plats: Varje plats ska ha ett namn (obligatoriskt), tillhöra en befintlig kategori,
##ha ett användar-ID för den användare som lagt till platsen, en status för
##privat/publik (standardinställning), datum och tid för senaste ändring, en
##beskrivning, koordinater som spatial data, samt datum och tid för när platsen skapades.

/*CREATE TABLE IF NOT EXISTS users(
                                    id INTEGER AUTO_INCREMENT PRIMARY KEY ,
                                    username VARCHAR(255),
                                    password VARCHAR(255),
                                    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS api_key(
    id INT AUTO_INCREMENT PRIMARY KEY,
    api_key VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    valid_until DATETIME NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) */

