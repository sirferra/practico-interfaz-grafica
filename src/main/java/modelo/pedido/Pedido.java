package modelo.pedido;

import java.util.List;
import java.util.Date;
import modelo.persona.Cliente;
import modelo.persona.Vendedor;

public class Pedido {
    public Date fecha;
    public Vendedor vendedor;
    public Cliente cliente;
    public EstadoPedido estado;
    public double totalPedido;
    public List<DetallePedido> detalles;

    public Pedido(Date fecha, Vendedor vendedor, Cliente cliente, EstadoPedido estado, double totalPedido, List<DetallePedido> detalles) {
        this.fecha = fecha;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.estado = estado;
        this.totalPedido = totalPedido;
        this.detalles = detalles;
    }
}
