INSERT INTO categoria (id, nombre) VALUES (1, 'Ruta'), (2, 'Monta a'), (3, 'Ciclocross'), (4, 'BMX'), (5, 'Paseo');
INSERT INTO marca (id, nombre) VALUES (1, 'Specialized'), (2, 'Trek'), (3, 'Giant'), (4, 'Merida'), (5, 'Cannondale');
INSERT INTO modelo (id, nombre, marca_id) VALUES (1, 'Tarmac', 1), (2, 'Emonda', 2), (3, 'TCX', 3), (4, 'One-Forty', 4), (5, 'Supersix Evo', 5);
INSERT INTO proveedor (id, nombre, apellido, dni, telefono, email, cuit, nombre_fantasia) VALUES
    (1, 'Juan', 'Perez', 12345678, '1234-5678', 'jperez@proveedor.com', '30-12345678-1', 'Proveedores S.A.'),
    (2, 'Pedro', 'Garcia', 87654321, '8765-4321', 'pgarcia@proveedor.com', '30-87654321-1', 'Garcia y Asociados'),
    (3, 'Mar a', 'Gonzalez', 55555555, '5555-5555', 'mgonzalez@proveedor.com', '30-55555555-1', 'Gonzalez S.A.'),
    (4, 'Ana', 'Rodr guez', 44444444, '4444-4444', 'arodriguez@proveedor.com', '30-44444444-1', 'Rodr guez Hnos.'),
    (5, 'Carlos', 'L pez', 33333333, '3333-3333', 'clopez@proveedor.com', '30-33333333-1', 'L pez y C a.');
INSERT INTO producto (id, nombre, descripcion, categoria_id, modelo_id, proveedor_id) VALUES
    (1, 'Bicicleta Ruta', 'Bicicleta Ruta de alta competencia', 1, 1, 1),
    (2, 'Bicicleta Monta a', 'Bicicleta Monta a para monta as extremas', 2, 2, 2),
    (3, 'Bicicleta Ciclocross', 'Bicicleta Ciclocross para competencias', 3, 3, 3),
    (4, 'Bicicleta BMX', 'Bicicleta BMX para competencias', 4, 4, 4),
    (5, 'Bicicleta Paseo', 'Bicicleta Paseo para pasear', 5, 5, 5);
INSERT INTO vendedor (id, nombre, apellido, dni, telefono, email, cuil, sucursal) VALUES
    (1, 'Juan', 'Perez', 12345678, '1234-5678', 'jperez@vendedor.com', '20-12345678-1', 'Sucursal 1'),
    (2, 'Pedro', 'Garcia', 87654321, '8765-4321', 'pgarcia@vendedor.com', '20-87654321-1', 'Sucursal 2'),
    (3, 'Mar a', 'Gonzalez', 55555555, '5555-5555', 'mgonzalez@vendedor.com', '20-55555555-1', 'Sucursal 3'),
    (4, 'Ana', 'Rodr guez', 44444444, '4444-4444', 'arodriguez@vendedor.com', '20-44444444-1', 'Sucursal 4'),
    (5, 'Carlos', 'L pez', 33333333, '3333-3333', 'clopez@vendedor.com', '20-33333333-1', 'Sucursal 5');
INSERT INTO cliente (id, nombre, apellido, dni, telefono, email, cuil) VALUES
    (1, 'Juan', 'Perez', 12345678, '1234-5678', 'jperez@cliente.com', '20-12345678-1'),
    (2, 'Pedro', 'Garcia', 87654321, '8765-4321', 'pgarcia@cliente.com', '20-87654321-1'),
    (3, 'Mar a', 'Gonzalez', 55555555, '5555-5555', 'mgonzalez@cliente.com', '20-55555555-1'),
    (4, 'Ana', 'Rodr guez', 44444444, '4444-4444', 'arodriguez@cliente.com', '20-44444444-1'),
    (5, 'Carlos', 'L pez', 33333333, '3333-3333', 'clopez@cliente.com', '20-33333333-1');
