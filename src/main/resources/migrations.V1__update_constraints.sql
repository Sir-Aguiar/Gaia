CREATE TABLE users (
    userId     SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    name       VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    permission VARCHAR(255) NOT NULL
);