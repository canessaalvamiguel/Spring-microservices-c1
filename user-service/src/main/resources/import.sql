INSERT INTO users (username, password, enabled, first_name, last_name, email) VALUES ('miguel', '12345',1, 'Miguel', 'Canessa','user@test.com');
INSERT INTO users (username, password, enabled, first_name, last_name, email) VALUES ('admin', '12345',1, 'John', 'Doe', 'jhon.doe@bolsadeideas.com');

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 1);