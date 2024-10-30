
package modelo.persona;

import repositorio.RepositorioDeDatos;

public class Vendedor extends Persona {
    
    private String codigo;
    private String sucursal;
    
    
    public Vendedor(){
        super();
    }
    public Vendedor(String sucursal) {
        this.codigo = generarCodigo();
        this.sucursal = sucursal;
    }

    public Vendedor(String sucursal, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.codigo = generarCodigo();
        this.sucursal = sucursal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public String generarCodigo() {
        return "V-"+count();
    }
    
    private int count(){
        return (RepositorioDeDatos.arrPersonas!=null 
                && RepositorioDeDatos.arrPersonas.size()==0)?1:RepositorioDeDatos.arrPersonas.size()+1 ;
    }

    @Override
    public String toString() {
        return super.toString() + ";"+this.sucursal;
    }

    public static String getHeaders() {
        return Persona.getHeaders() + ";SUCURSAL";
    }
    
}
