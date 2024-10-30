package modelo.producto;

import modelo.persona.Proveedor;

public class Producto {
    String code;
    String name;
    String description;
    Categoria category;
    Proveedor proveedor;
    double price;
    String imagePath;
    String[] tags;
    int stock;
    Producto[] parts;
    Modelo modelo;

    public Producto(
        String code,
        String name,
        String description,
        Categoria category,
        Proveedor proveedor,
        double price,
        String imagePath,
        String[] tags,
        int stock,
        Producto[] parts,
        Modelo modelo
    ){

    }
}
