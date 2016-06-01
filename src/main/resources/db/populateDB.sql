DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM votes;
ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE user_roles_user_id_seq RESTART WITH 1;
ALTER SEQUENCE restaurants_id_seq RESTART WITH 1;
ALTER SEQUENCE dishes_id_seq RESTART WITH 1;

INSERT INTO restaurants (name) VALUES
  ('The Vintage Chef'),
  ('Roadhouse'),
  ('The Rose'),
  ('Meadows');

INSERT INTO users (username, password, name) VALUES
  ('ismael', '$2a$10$BX.wlvCnuTht/HmVoT5vaeayg7N8.K70XOkc4JVnAbwkosHHAbetS', 'Ismael Wilner'),
  ('noble', '$2a$10$3cjTslaRSEZTysooOT.9XeUx/g..rAFZpwS6G3u13r.ArsbQms/RW', 'Noble Popovich'),
  ('estrella', '$2a$10$7.Ew1ea/Gw/QopwnsHBkD.GY91XLafzIJHNOkZAT8.GT3cOQx0kce', 'Estrella Gosnell'),
  ('sondra', '$2a$10$llriUGjZnhwCL4Obl6s/s.458.XD9i.n1E3sxm55kvV34SjRjc5DC', 'Sondra Jorstad'),
  ('babette', '$2a$10$ATm/6BfnN0qWnN/GtXPBn.eWUF6CdUNp6agKYeDUG6/Ak.yWoTHxC', 'Babette Merlin'),
  ('elke', '$2a$10$3wko11Pop7RvWpLJJ/c0jeEOg0d.5ZqVqb/EMsmSwXIRCgNVs94JC', 'Elke Weick'),
  ('otha', '$2a$10$bE.jGZO6iGg6AgCvogUOZeVPvUgXtIcIvIiqb9WcmyiC7QfpKKyUO', 'Otha Manchester'),
  ('cedric', '$2a$10$pfUF3l6RAmPEKyOrBdi0zeKRbdVWc6CRzTO6qNygWXIMcgTAWCFo2', 'Cedric Lainez'),
  ('jeanmarie', '$2a$10$CmV.WNWvQnD9k8Xmab3iju0/4Q9CN8p0ASS8abuSkcRK.PjZC.qhm', 'Jeanmarie Schuetz'),
  ('kelly', '$2a$10$YylF.MQ8SlQEQuEWTvk2KOvVb.oz/BTG7V1UPeZAmQyXna6VeLXxq', 'Kelly Rothschild');

INSERT INTO user_roles (user_id, role) VALUES
  (1, 'ROLE_ADMIN'),
  (1, 'ROLE_USER'),
  (2, 'ROLE_USER'),
  (3, 'ROLE_USER'),
  (4, 'ROLE_USER'),
  (5, 'ROLE_USER'),
  (6, 'ROLE_USER'),
  (7, 'ROLE_USER'),
  (8, 'ROLE_ADMIN'),
  (8, 'ROLE_USER'),
  (9, 'ROLE_ADMIN'),
  (10, 'ROLE_USER');

INSERT INTO dishes (name, price, restaurant_id) VALUES
  ('Slow-Cooked Mint & Mustard Turkey', 374, 1),
  ('Marinated Lime-Coated Horse', 355, 2),
  ('Steamed Coconut Frog', 994, 1),
  ('Basted Sour Clams', 431, 4),
  ('Grilled Mountain Risotto', 569, 3),
  ('Brined Mustard & Garlic Spring Greens', 408, 2),
  ('Coconut and Banana Pound Cake', 558, 1),
  ('Vanilla and Date Wafer', 720, 3),
  ('Praline Ice Cream', 219, 2),
  ('White Chocolate Snacks', 693, 4);

INSERT INTO votes (user_id, restaurant_id) VALUES
  (1, 3),
  (2, NULL),
  (3, 4),
  (4, 1),
  (5, 2),
  (6, 1),
  (7, NULL),
  (8, 4),
  (9, 2),
  (10, 4);
