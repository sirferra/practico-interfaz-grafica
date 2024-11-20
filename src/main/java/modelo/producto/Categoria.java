package modelo.producto;

import java.util.HashMap;
import java.util.Map;

import negocio.mysql.Column;
import negocio.mysql.Table;

@Table(name = "categoria")
public class Categoria {

    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    public Categoria() {
    }
    
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("nombre", nombre);
        return map;
    }

    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return this.getNombre();
    }
}

