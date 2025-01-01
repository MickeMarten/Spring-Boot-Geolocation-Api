REST API with Spring Boot and MySQL
A RESTful API built with Spring Boot 3.x.x, Java 23, and MySQL to manage points of interest and categories. The API supports secure data access and user-specific functionalities with role-based authorization using OAuth2 and JWT.

Features
Data Model
Category: Includes name (unique), symbol (emoji), and description.
Locations: Includes name, category, user ID, status (public/private), description, coordinates (spatial data), and timestamps (created/updated).

Endpoints

Categories
GET: Fetch all or a specific category.
POST: Create a new category (admin role).

Locations
GET:
Fetch public places or a specific public place.
Fetch places in a category or area.
Fetch private/public places for the logged-in user.
POST: Add a new place (login required).
PUT: Update an existing place (login required).
DELETE: Remove a place (login required, soft delete supported).

Security
Anonymous users can only access public places.
Logged-in users can access both public and private places.
Admin users can manage categories.

Setup
Prerequisites
Java 23
MySQL-server (Check compose.yaml for database enviroment)
Maven
