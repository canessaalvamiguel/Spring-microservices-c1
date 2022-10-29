INSERT INTO users (username, password, enabled, first_name, last_name, email) VALUES ('miguel', '$2a$10$9fwqMao9clif4Tssu7ZI1.ZI78UrOyYKatl3bp/lBKIoyXyoPz522',true, 'Miguel', 'Canessa','user@test.com');
INSERT INTO users (username, password, enabled, first_name, last_name, email) VALUES ('admin', '$2a$10$MWD46xGjIjvA5SufBTSL0e5EJDJKJdPG3HUS6I3IJmFewUI9ZI6t.',true, 'John', 'Doe', 'jhon.doe@test.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);