package modelo.producto;

import modelo.persona.Proveedor;
import negocio.mysql.Column;
import negocio.mysql.Table;

@Table(name = "producto")
public class Producto {
    @Column(name = "codigo")
    String code;
    @Column(name = "nombre")
    String name;
    @Column(name = "descripcion")
    String description;
    @Column(name = "categoria")
    Categoria category;
    @Column(name = "proveedor")
    Proveedor proveedor;
    @Column(name = "precio")
    double price;
    @Column(name = "imagen")
    String imagePath;
    @Column(name = "etiquetas")
    String[] tags;
    @Column(name = "stock")
    int stock;
    @Column(name = "componentes")
    Producto[] parts;
    @Column(name = "modelo")
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
