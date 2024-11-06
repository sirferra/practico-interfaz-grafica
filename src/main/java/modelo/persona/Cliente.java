package modelo.persona;

import java.util.Map;

import repositorio.RepositorioDeDatos;



public class Cliente extends Persona{
    private String codigo;
    private String cuil;
    
    
    public Cliente(){
        super();
        this.table = "clientes";
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = super.toMap();
        map.put("cuil", cuil); 
        return map;
    }
            
    public Cliente(String cuil) {
        this.codigo = generarCodigo();
        this.cuil = cuil;
        this.table = "clientes";
    }

    public Cliente(String cuil, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.codigo = generarCodigo();
        this.cuil = cuil;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    @Override
    public String generarCodigo() {
       return "C-"+contarClientes();
    }
    
    private int contarClientes(){
        Cliente[] arrClients = RepositorioDeDatos.arrPersonas
                                .stream()
                                .filter(p-> p instanceof Cliente)
                                .toArray(Cliente[]::new);
        return arrClients.length;
    }
    
    @Override
    public String toString(){
        return super.toString() + ";"+this.codigo+ ";"+this.cuil;
    }

    public static String getHeaders() {
        return Persona.getHeaders() +";CODIGO;CUIL";
    }
}
