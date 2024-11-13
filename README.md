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
Producto : +codigo: String
Producto : +nombre: String
Producto : +descripcion: String
Producto : +categoria: Categoria
Producto : +proveedor: Proveedor
Producto : +precio: double
Producto : +imagen: String
Producto : +etiquetas: String[]
Producto : +stock: int
Producto : +componentes: Producto[]
Producto : +modelo: Modelo

class Categoria
Categoria : +nombre: category
Categoria : +descripcion: string
Categoria : +tags: String[]
Categoria : +type: enum[]

class Modelo
Modelo : +modelo: String
Modelo : +descripcion: String
Modelo : +marca: Marca

class Marca
Marca : +cod: String
Marca : +name: String

class Order
Order : +date: Date
Order : +seller: Vendedor
Order : +client: Cliente
Order : +status: enum[]
Order : +details: Detalle[]

class Order_details
Order_details : +product: Producto
Order_details : +quantity: int
Order_details : +price: double
Order_details : +discount: int

%% Relaciones

Persona <|-- Vendedor
Persona <|-- Cliente
Producto --> Categoria
Producto --> Proveedor
Order --> Order_details
Order_details --> Producto
Modelo --> Marca
Order --> Vendedor
Order --> Cliente
```