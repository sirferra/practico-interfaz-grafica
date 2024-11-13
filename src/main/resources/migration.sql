INSERT INTO categoria (id, nombre) VALUES (1, 'Ruta'), (2, 'Monta a'), (3, 'Ciclocross'), (4, 'BMX'), (5, 'Paseo');
INSERT INTO marca (id, nombre) VALUES (1, 'Specialized'), (2, 'Trek'), (3, 'Giant'), (4, 'Merida'), (5, 'Cannondale');
INSERT INTO modelo (id, nombre, marca_id) VALUES (1, 'Tarmac', 1), (2, 'Emonda', 2), (3, 'TCX', 3), (4, 'One-Forty', 4), (5, 'Supersix Evo', 5);
INSERT INTO proveedor (id, nombre, apellido, dni, telefono, email, cuit, nombre_fantasia) VALUES
    (1, 'Juan', 'Perez', 12345678, '1234-5678', 'jperez@proveedor.com', '30-12345678-1', 'Proveedores S.A.'),
    (2, 'Pedro', 'Garcia', 87654321, '8765-4321', 'pgarcia@proveedor.com', '30-87654321-1', 'Garcia y Asociados'),
    (3, 'Mar a', 'Gonzalez', 55555555, '5555-5555', 'mgonzalez@proveedor.com', '30-55555555-1', 'Gonzalez S.A.'),
    (4, 'Ana', 'Rodr guez', 44444444, '4444-4444', 'arodriguez@proveedor.com', '30-44444444-1', 'Rodr guez Hnos.'),
    (5, 'Carlos', 'L pez', 33333333, '3333-3333', 'clopez@proveedor.com', '30-33333333-1', 'L pez y C a.');
INSERT INTO producto (id, codigo, nombre, descripcion, categoria_id, proveedor_id, precio, imagen, etiquetas, stock, modelo_id) VALUES
    (1, 'RUTA-1', 'Bicicleta Ruta', 'Bicicleta Ruta de alta competencia', 1, 1, 5000.0, 'ruta1.jpg', 'ruta,competencia', 10, 1),
    (2, 'MONTA-1', 'Bicicleta Monta a', 'Bicicleta Monta a para monta as extremas', 2, 2, 3000.0, 'montana1.jpg', 'monta a,extrema', 15, 2),
    (3, 'CICLO-1', 'Bicicleta Ciclocross', 'Bicicleta Ciclocross para competencias', 3, 3, 4000.0, 'ciclocross1.jpg', 'ciclocross,competencia', 12, 3),
    (4, 'BMX-1', 'Bicicleta BMX', 'Bicicleta BMX para competencias', 4, 4, 2000.0, 'bmx1.jpg', 'bmx,competencia', 8, 4),
    (5, 'PASEO-1', 'Bicicleta Paseo', 'Bicicleta Paseo para pasear', 5, 5, 1500.0, 'paseo1.jpg', 'paseo,familiar', 20, 5),
    (6, 'RUTA-2', 'Bicicleta Ruta 2', 'Bicicleta Ruta de alta competencia', 1, 1, 5000.0, 'ruta2.jpg', 'ruta,competencia', 10, 1),
    (7, 'MONTA-2', 'Bicicleta Monta a 2', 'Bicicleta Monta a para monta as extremas', 2, 2, 3000.0, 'montana2.jpg', 'monta a,extrema', 15, 2),
    (8, 'CICLO-2', 'Bicicleta Ciclocross 2', 'Bicicleta Ciclocross para competencias', 3, 3, 4000.0, 'ciclocross2.jpg', 'ciclocross,competencia', 12, 3),
    (9, 'BMX-2', 'Bicicleta BMX 2', 'Bicicleta BMX para competencias', 4, 4, 2000.0, 'bmx2.jpg', 'bmx,competencia', 8, 4),
    (10, 'PASEO-2', 'Bicicleta Paseo 2', 'Bicicleta Paseo para pasear', 5, 5, 1500.0, 'paseo2.jpg', 'paseo,familiar', 20, 5),
    (11, 'RUTA-3', 'Bicicleta Ruta 3', 'Bicicleta Ruta de alta competencia', 1, 1, 5000.0, 'ruta3.jpg', 'ruta,competencia', 10, 1),
    (12, 'MONTA-3', 'Bicicleta Monta a 3', 'Bicicleta Monta a para monta as extremas', 2, 2, 3000.0, 'montana3.jpg', 'monta a,extrema', 15, 2),
    (13, 'CICLO-3', 'Bicicleta Ciclocross 3', 'Bicicleta Ciclocross para competencias', 3, 3, 4000.0, 'ciclocross3.jpg', 'ciclocross,competencia', 12, 3),
    (14, 'BMX-3', 'Bicicleta BMX 3', 'Bicicleta BMX para competencias', 4, 4, 2000.0, 'bmx3.jpg', 'bmx,competencia', 8, 4),
    (15, 'PASEO-3', 'Bicicleta Paseo 3', 'Bicicleta Paseo para pasear', 5, 5, 1500.0, 'paseo3.jpg', 'paseo,familiar', 20, 5),
    (16, 'RUTA-4', 'Bicicleta Ruta 4', 'Bicicleta Ruta de alta competencia', 1, 1, 5000.0, 'ruta4.jpg', 'ruta,competencia', 10, 1),
    (17, 'MONTA-4', 'Bicicleta Monta a 4', 'Bicicleta Monta a para monta as extremas', 2, 2, 3000.0, 'montana4.jpg', 'monta a,extrema', 15, 2),
    (18, 'CICLO-4', 'Bicicleta Ciclocross 4', 'Bicicleta Ciclocross para competencias', 3, 3, 4000.0, 'ciclocross4.jpg', 'ciclocross,competencia', 12, 3),
    (19, 'BMX-4', 'Bicicleta BMX 4', 'Bicicleta BMX para competencias', 4, 4, 2000.0, 'bmx4.jpg', 'bmx,competencia', 8, 4),
    (20, 'PASEO-4', 'Bicicleta Paseo 4', 'Bicicleta Paseo para pasear', 5, 5, 1500.0, 'paseo4.jpg', 'paseo,familiar', 20, 5),
    (21, 'RUTA-5', 'Bicicleta Ruta 5', 'Bicicleta Ruta de alta competencia', 1, 1, 5000.0, 'ruta5.jpg', 'ruta,competencia', 10, 1),
    (22, 'MONTA-5', 'Bicicleta Monta a 5', 'Bicicleta Monta a para monta as extremas', 2, 2, 3000.0, 'montana5.jpg', 'monta a,extrema', 15, 2),
    (23, 'CICLO-5', 'Bicicleta Ciclocross 5', 'Bicicleta Ciclocross para competencias', 3, 3, 4000.0, 'ciclocross5.jpg', 'ciclocross,competencia', 12, 3),
    (24, 'BMX-5', 'Bicicleta BMX 5', 'Bicicleta BMX para competencias', 4, 4, 2000.0, 'bmx5.jpg', 'bmx,competencia', 8, 4),
    (25, 'PASEO-5', 'Bicicleta Paseo 5', 'Bicicleta Paseo para pasear', 5, 5, 1500.0, 'paseo5.jpg', 'paseo,familiar', 20, 5),
    (26, 'RUTA-6', 'Bicicleta Ruta 6', 'Bicicleta Ruta de alta competencia', 1, 1, 5000.0, 'ruta6.jpg', 'ruta,competencia', 10, 1),
    (27, 'MONTA-6', 'Bicicleta Monta a 6', 'Bicicleta Monta a para monta as extremas', 2, 2, 3000.0, 'montana6.jpg', 'monta a,extrema', 15, 2),
    (28, 'CICLO-6', 'Bicicleta Ciclocross 6', 'Bicicleta Ciclocross para competencias', 3, 3, 4000.0, 'ciclocross6.jpg', 'ciclocross,competencia', 12, 3),
    (29, 'BMX-6', 'Bicicleta BMX 6', 'Bicicleta BMX para competencias', 4, 4, 2000.0, 'bmx6.jpg', 'bmx,competencia', 8, 4),
    (30, 'PASEO-6', 'Bicicleta Paseo 6', 'Bicicleta Paseo para pasear', 5, 5, 1500.0, 'paseo6.jpg', 'paseo,familiar', 20, 5);
INSERT INTO vendedor (id, nombre, apellido, dni, telefono, email, cuil, sucursal) VALUES
    (1, 'Jorge', 'Martinez', 11111111, '1111-1111', 'jmartinez@vendedor.com', '20-11111111-1', 'Sucursal 1'),
    (2, 'Laura', 'Gonzalez', 22222222, '2222-2222', 'lgonzalez@vendedor.com', '20-22222222-1', 'Sucursal 2'),
    (3, 'Fernando', 'Rodr guez', 33333333, '3333-3333', 'frodriguez@vendedor.com', '20-33333333-1', 'Sucursal 3'),
    (4, 'Andrea', 'Perez', 44444444, '4444-4444', 'aperez@vendedor.com', '20-44444444-1', 'Sucursal 4'),
    (5, 'Miguel', 'L pez', 55555555, '5555-5555', 'mlopez@vendedor.com', '20-55555555-1', 'Sucursal 5');
INSERT INTO cliente (id, nombre, apellido, dni, telefono, email, cuil) VALUES
    (1, 'Luis', 'Hernandez', 98765432, '9876-5432', 'lhernandez@cliente.com', '20-98765432-1'),
    (2, 'Sofia', 'Ramirez', 87654322, '8765-4322', 'sramirez@cliente.com', '20-87654322-1'),
    (3, 'Diego', 'Fernandez', 76543210, '7654-3210', 'dfernandez@cliente.com', '20-76543210-1'),
    (4, 'Julia', 'Martinez', 65432109, '6543-2109', 'jmartinez@cliente.com', '20-65432109-1'),
    (5, 'Mateo', 'Lopez', 54321098, '5432-1098', 'mlopez@cliente.com', '20-54321098-1');
INSERT INTO pedido (id, cliente_id, vendedor_id, fecha, total, estado) VALUES
    (1, 1, 1, '2022-01-01', 8000.0, 'PREPARACION'),
    (2, 2, 2, '2022-01-02', 3000.0, 'CANCELADO'),
    (3, 3, 3, '2022-01-03', 4000.0, 'ENTREGADO'),
    (4, 4, 4, '2022-01-04', 2000.0, 'PREPARACION'),
    (5, 5, 5, '2022-01-05', 1500.0, 'CANCELADO');
INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio) VALUES
    (1, 1, 1, 5000.0),
    (1, 2, 1, 3000.0),
    (2, 2, 2, 3000.0),
    (3, 3, 3, 4000.0),
    (4, 4, 1, 2000.0),
    (5, 5, 2, 1500.0);