package modelo.pedido;

import modelo.producto.Producto;

public class DetallePedido {
    public Producto producto;
    public int cantidad;
    public double precio;
    public int descuento;

    public DetallePedido(Producto producto, int cantidad, double precio, int descuento) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
    }

}
