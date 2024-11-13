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