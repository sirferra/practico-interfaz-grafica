
package modelo.persona;

import java.util.Calendar;

import repositorio.RepositorioDeDatos;

public class Proveedor extends Persona {
    
    private String codigo;
    private String nombreFantasia;
    private String cuit;

    public Proveedor(String codigo, String nombreFantasia, String cuit) {
        this.codigo = codigo;
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    public Proveedor(String codigo, String nombreFantasia, String cuit, String nombre, String apellido, int dni) {
        super(nombre, apellido, dni,"", "");
        this.codigo = codigo;
        this.nombreFantasia = nombreFantasia;
        this.cuit = cuit;
    }

    @Override
    public String generarCodigo() {
        Calendar calendar=Calendar.getInstance();
        int anio=calendar.get(Calendar.YEAR);
        return "P-"+anio+"-"+contarProveedores();
    }
    
     private int contarProveedores(){
        return (RepositorioDeDatos.arrPersonas!=null 
                && RepositorioDeDatos.arrPersonas.size()==0)?1:RepositorioDeDatos.arrPersonas.size()+1 ;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        return super.toString() + ";"+this.codigo+ ";"+this.cuit+";"+this.nombreFantasia;
    }

    public static String getHeaders() {
        return Persona.getHeaders()+";CODIGO;CUIT;NOMBRE FANTASIA";
    }
}
