package modelo.pedido;

import java.util.List;
import java.util.Date;
import modelo.persona.Cliente;
import modelo.persona.Vendedor;
import negocio.mysql.Column;
import negocio.mysql.Table;

@Table(name = "pedido")
public class Pedido {
    @Column(name = "fecha")
    public Date fecha;
    @Column(name = "vendedor_id")
    public Vendedor vendedor;
    @Column(name = "cliente_id")
    public Cliente cliente;
    @Column(name = "estado")
    public EstadoPedido estado;
    @Column(name = "total_pedido")
    public double totalPedido;
    @Column(name = "detalles")
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
