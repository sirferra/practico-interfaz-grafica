package modelo.producto;

import modelo.persona.Proveedor;
import negocio.mysql.Column;
import negocio.mysql.Id;
import negocio.mysql.Table;

@Table(name = "producto")
public class Producto {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "codigo")
    String codigo;
    @Column(name = "nombre")
    String nombre;
    @Column(name = "descripcion")
    String descripcion;
    @Column(name = "categoria")
    Categoria categoria;
    @Column(name = "proveedor")
    Proveedor proveedor;
    @Column(name = "precio")
    double precio;
    @Column(name = "imagen")
    String imagen;
    @Column(name = "etiquetas")
    String etiquetas;
    @Column(name = "stock")
    int stock;
    @Column(name = "modelo")
    Modelo modelo;

    public Producto(
        String codigo,
        String nombre,
        String descripcion,
        Categoria categoria,
        Proveedor proveedor,
        double precio,
        String imagen,
        String etiquetas,
        int stock,
        Producto[] componentes,
        Modelo modelo
    ){
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.proveedor = proveedor;
        this.precio = precio;
        this.imagen = imagen;
        this.etiquetas = etiquetas;
        this.stock = stock;
        this.modelo = modelo;
    }
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }
    public Producto() {}
    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String etiquetas) {
        this.etiquetas = etiquetas;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}

