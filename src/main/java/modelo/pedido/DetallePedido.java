package modelo.pedido;

import modelo.producto.Producto;
import negocio.mysql.Column;
import negocio.mysql.Table;

@Table(name = "detalle_pedido")
public class DetallePedido {
    @Column(name = "producto_id")
    public Producto producto;
    @Column(name = "cantidad")
    public int cantidad;
    @Column(name = "precio")
    public double precio;
    @Column(name = "descuento")
    public int descuento;

    public DetallePedido(Producto producto, int cantidad, double precio, int descuento) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
    }

}

