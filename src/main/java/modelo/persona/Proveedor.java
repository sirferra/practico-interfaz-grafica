
package modelo.persona;

import java.util.Map;

import negocio.mysql.Column;
import negocio.mysql.Id;
import negocio.mysql.Table;

@Table(name = "proveedores")
public class Proveedor extends Persona {
    @Id
    @Column(name = "id")
    private int id;


    @Column(name = "nombreFantasia")
    private String nombreFantasia;
    
    @Column(name = "cuit")
    private String cuit;

    public Proveedor() {}

    public Proveedor(String codigo, String nombreFantasia, String cuit) {
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = super.toMap();
        map.put("cuit", cuit); 
        map.put("nombreFantasia", nombreFantasia);
        return map;
    }

    public Proveedor(String codigo, String nombreFantasia, String cuit, String nombre, String apellido, int dni) {
        super(nombre, apellido, dni,"", "");
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    public String getNombreFantasia() {
        return nombreFantasia;
    }

    public void setNombreFantasia(String nombreFantasia) {
        this.nombreFantasia = nombreFantasia;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }
   
    @Override
    public String toString(){
        return super.toString() + ";"+this.cuit+";"+this.nombreFantasia;
    }

    public static String getHeaders() {
        return Persona.getHeaders()+";CODIGO;CUIT;NOMBRE FANTASIA";
    }
}
