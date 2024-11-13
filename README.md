# Practico interfaz grafica
Para IES 21

<br>
<br>

### How to run
Asegurate de levantar una base de datos. <br>
Podés utilizar el docker-compose que dejé para hacerlo<br>
```bash
docker-compose up -d
```
Si no tenés docker instalado, te dejo una guía para instalarlo y poder correrlo<br>
[¿Cómo instalar Docker en windows?](https://learn.microsoft.com/es-es/virtualization/windowscontainers/manage-docker/configure-docker-daemon)
<br>
Lo siguiente es cambiar el string de conexión en:

```bash
src\main\java\negocio\mysql\ConnectionHolder.java:7
```
```mermaid
classDiagram

%% Clases y Atributos

class Persona
Persona : +nombre
Persona : +apellido
Persona : +edad

class Vendedor
Vendedor : +sucursal
Vendedor : +ventas

class Cliente
Cliente : +direccion
Cliente : +telefono

class Proveedor
Proveedor : +empresa
Proveedor : +contacto

class Producto
Producto : +codigo
Producto : +nombre
Producto : +descripcion
Producto : +categoria
Producto : +proveedor
Producto : +precio
Producto : +imagen
Producto : +etiquetas
Producto : +stock
Producto : +componentes
Producto : +modelo

class Categoria
Categoria : +nombre
Categoria : +descripcion
Categoria : +tags
Categoria : +type

class Modelo
Modelo : +modelo
Modelo : +descripcion
Modelo : +marca

class Marca
Marca : +cod
Marca : +name

class Order
Order : +date
Order : +seller
Order : +client
Order : +status
Order : +details

class Order_details
Order_details : +product
Order_details : +quantity
Order_details : +price
Order_details : +discount

%% Relaciones

Persona <|-- Vendedor
Persona <|-- Cliente
Persona <|-- Proveedor
Producto --> Categoria
Producto --> Proveedor
Modelo --> Marca
Order --> Vendedor
Order --> Cliente
Order --> Order_details
Order_details --> Producto
```


DER de la base de datos:
```mermaid
erDiagram
    CATEGORIA {
        INT id PK "Auto Increment"
        VARCHAR nombre "255, Not Null"
    }
    
    MARCA {
        INT id PK "Auto Increment"
        VARCHAR nombre "255, Not Null"
    }
    
    MODELO {
        INT id PK "Auto Increment"
        VARCHAR nombre "255, Not Null"
        INT marca_id FK
    }
    MODELO }|..|| MARCA : "marca_id"
    
    PROVEEDOR {
        INT id PK "Auto Increment"
        VARCHAR nombre "255, Not Null"
        VARCHAR apellido "255, Not Null"
        INT dni "Not Null"
        VARCHAR telefono "255, Not Null"
        VARCHAR email "255, Not Null"
        VARCHAR cuit "255, Not Null"
        VARCHAR nombre_fantasia "255, Not Null"
    }
    
    PRODUCTO {
        INT id PK "Auto Increment"
        VARCHAR codigo "255"
        VARCHAR nombre "255, Not Null"
        VARCHAR descripcion "255"
        INT categoria_id FK
        INT proveedor_id FK
        DOUBLE precio
        VARCHAR imagen "255"
        VARCHAR etiquetas "255"
        INT stock
        INT modelo_id FK
    }
    PRODUCTO }|..|| CATEGORIA : "categoria_id"
    PRODUCTO }|..|| PROVEEDOR : "proveedor_id"
    PRODUCTO }|..|| MODELO : "modelo_id"
    
    VENDEDOR {
        INT id PK "Auto Increment"
        VARCHAR nombre "255, Not Null"
        VARCHAR apellido "255, Not Null"
        INT dni "Not Null, Unique"
        VARCHAR telefono "255, Null"
        VARCHAR email "255, Null"
        VARCHAR cuil "255, Null"
        VARCHAR sucursal "255, Null"
    }
    
    CLIENTE {
        INT id PK "Auto Increment"
        VARCHAR nombre "255, Not Null"
        VARCHAR apellido "255, Not Null"
        INT dni "Not Null, Unique"
        VARCHAR telefono "255, Not Null"
        VARCHAR email "255, Not Null"
        VARCHAR cuil "255, Not Null"
    }
    
    PEDIDO {
        INT id PK "Auto Increment"
        INT cliente_id FK
        INT vendedor_id FK
        DATE fecha "Not Null"
        DECIMAL total "10,2, Not Null"
        VARCHAR estado "25, Not Null"
    }
    PEDIDO }|..|| CLIENTE : "cliente_id"
    PEDIDO }|..|| VENDEDOR : "vendedor_id"
    
    DETALLE_PEDIDO {
        INT pedido_id PK_FK
        INT producto_id PK_FK
        INT cantidad "Not Null"
        DECIMAL precio "10,2, Not Null"
    }
    DETALLE_PEDIDO }|..|| PEDIDO : "pedido_id"
    DETALLE_PEDIDO }|..|| PRODUCTO : "producto_id"
```