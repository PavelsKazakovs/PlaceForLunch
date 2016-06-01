DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS votes;

CREATE TABLE restaurants (
  id   SERIAL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE users (
  id       SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  name     VARCHAR NOT NULL
);
CREATE UNIQUE INDEX users_unique_username_idx ON users (username);

CREATE TABLE user_roles (
  user_id SERIAL NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role)
);

CREATE TABLE dishes (
  id            SERIAL PRIMARY KEY,
  name          VARCHAR,
  price         INTEGER,
  restaurant_id INTEGER REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE votes (
  user_id       INTEGER PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
  restaurant_id INTEGER DEFAULT NULL REFERENCES restaurants (id) ON DELETE SET DEFAULT
);
