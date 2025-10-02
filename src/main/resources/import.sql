INSERT INTO tb_address(street, city, state, country, zipcode) VALUES ('Cassia Street', 'Maringá', 'Paraná', 'Brazil', '87191000');
INSERT INTO tb_address(street, city, state, country, zipcode) VALUES ('Mauá Avenue', 'Maringá', 'Paraná', 'Brazil', '87211000');

INSERT INTO tb_user(first_name, last_name, email, phone, role, address_id, created_at, updated_at) VALUES ('Vitor', 'Hugo', 'vitor.hugo@email.com', '44987654321', 'ADMIN', 1, '2025-10-01T12:00:00Z', null);
INSERT INTO tb_user(first_name, last_name, email, phone, role, address_id, created_at, updated_at) VALUES ('Alexandre', 'Tavares', 'ale.tavares@email.com', '44912345678', 'CUSTOMER', 2, '2025-10-01T12:00:00Z', null);

INSERT INTO tb_category(name) VALUES ('Eletronics');
INSERT INTO tb_category(name) VALUES ('Office');

INSERT INTO tb_product(name, description, price, stock, img_url, active, created_at, updated_at) VALUES ('Mouse B154', 'Office optical mouse', 12.50, 20, 'http://somesite.com/img', true, '2025-10-02T12:00:00Z', null);
INSERT INTO tb_product(name, description, price, stock, img_url, active, created_at, updated_at) VALUES ('Keyboard Mecha12', 'Mechanical keyboard', 200.00, 10, 'http://somesite.com/img', true, '2025-10-02T12:00:00Z', null);
INSERT INTO tb_product(name, description, price, stock, img_url, active, created_at, updated_at) VALUES ('Smart TV H20', 'Smart TV with high resolution', 1200.00, 5, 'http://somesite.com/img', true, '2025-10-02T12:00:00Z', null);

INSERT INTO tb_product_category(product_id, category_id) VALUES (1,1);
INSERT INTO tb_product_category(product_id, category_id) VALUES (1,2);
INSERT INTO tb_product_category(product_id, category_id) VALUES (2,1);
INSERT INTO tb_product_category(product_id, category_id) VALUES (3,1);