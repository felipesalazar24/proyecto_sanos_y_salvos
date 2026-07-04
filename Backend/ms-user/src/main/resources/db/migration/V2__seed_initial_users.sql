-- V2__insert_chilean_users.sql

INSERT INTO users (
    name, 
    last_name, 
    email, 
    password, 
    phone_number, 
    address, 
    address_number, 
    city, 
    country, 
    role
) VALUES 
(
    'felipe',
    'salazar',
    'felipe@duocuc.cl',
    '$2a$10$8.27G6B5Kx.t5u7g8H9iO.eF0D2B8V8M3L0yXh7B6T8W4R1Q7Z8G.',
    987654321, 
    'Avenida Vicuña Mackenna', 
    7500, 
    'La Florida', 
    'Chile', 
    'user'
),
(
    'Francisca', 
    'Araya', 
    'francisca.araya@gmail.com', 
    '$2a$12$e0MvK2...', 
    912345678, 
    'Paseo Ahumada', 
    341, 
    'Santiago', 
    'Chile', 
    'user'
),
(
    'Rodrigo', 
    'Silva', 
    'rodrigo.silva@gmail.com', 
    '$2a$12$e0MvK2...', 
    955566677, 
    'Avenida Grecia', 
    8500, 
    'Peñalolén', 
    'Chile', 
    'user'
),
(
    'Camila', 
    'Rojas', 
    'camila.rojas@gmail.com', 
    '$2a$12$e0MvK2...', 
    944433322, 
    'Avenida Providencia', 
    1650, 
    'Providencia', 
    'Chile', 
    'user'
),
(
    'Matías', 
    'Contreras', 
    'matias.contreras@gmail.com', 
    '$2a$12$e0MvK2...', 
    922288811, 
    'Gran Avenida', 
    5200, 
    'San Miguel', 
    'Chile', 
    'user'
);