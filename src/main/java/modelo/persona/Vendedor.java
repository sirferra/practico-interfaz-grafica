
package modelo.persona;

import java.util.Map;

import negocio.mysql.Column;
import negocio.mysql.Id;

public class Vendedor extends Persona {    
    private String sucursal;
    @Id
    @Column(name = "id")
    private int id;
    
    public Vendedor(){}
    public Vendedor(String sucursal) {
        this.sucursal = sucursal;
    }
        @Override
    public Map<String, String> toMap() {
        Map<String, String> map = super.toMap();
        map.put("sucursal", sucursal);
        return map;
    }

    public Vendedor(String sucursal, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.sucursal = sucursal;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String toString() {
        return super.toString() + ";"+this.sucursal;
    }

    public static String getHeaders() {
        return Persona.getHeaders() + ";SUCURSAL";
    }
    
}
