package modelo.producto;

import java.util.HashMap;
import java.util.Map;

import negocio.mysql.Column;
import negocio.mysql.Table;

@Table(name="modelo")
public class Modelo {
    @Column(name="id")
    private int id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="marca_id")
    private int marcaId;

    public Modelo() {
    }

    public Modelo(String nombre, int marcaId) {
        this.nombre = nombre;
        this.marcaId = marcaId;
    }

    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("nombre", nombre);
        map.put("marca_id", marcaId);
        return map;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(int marcaId) {
        this.marcaId = marcaId;
    }
}
