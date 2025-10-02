INSERT INTO tb_address(street, city, state, country, zipcode) VALUES ('Cassia Street', 'Maringá', 'Paraná', 'Brazil', '87191000');
INSERT INTO tb_address(street, city, state, country, zipcode) VALUES ('Mauá Avenue', 'Maringá', 'Paraná', 'Brazil', '87211000');

INSERT INTO tb_user(first_name, last_name, email, phone, role, address_id, created_at, updated_at) VALUES ('Vitor', 'Hugo', 'vitor.hugo@email.com', '44987654321', 'ADMIN', 1, '2025-10-01T12:00:00Z', null);
INSERT INTO tb_user(first_name, last_name, email, phone, role, address_id, created_at, updated_at) VALUES ('Alexandre', 'Tavares', 'ale.tavares@email.com', '44912345678', 'CUSTOMER', 2, '2025-10-01T12:00:00Z', null);

INSERT INTO tb_category(name) VALUES ('Eletronics');
INSERT INTO tb_category(name) VALUES ('Office');
