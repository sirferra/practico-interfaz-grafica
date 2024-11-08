package modelo.persona;

import java.util.Map;

import negocio.mysql.Column;
import negocio.mysql.Table;

@Table(name = "cliente")
public class Cliente extends Persona{

    @Column(name = "cuil")
    private String cuil;
    
    
    public Cliente(){}

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = super.toMap();
        map.put("cuil", cuil); 
        return map;
    }
            
    public Cliente(String cuil) {
        this.cuil = cuil;
    }

    public Cliente(String cuil, String nombre, String apellido, int dni, String telefono, String email) {
        super(nombre, apellido, dni, telefono, email);
        this.cuil = cuil;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    
    @Override
    public String toString(){
        return super.toString() + ";"+this.cuil;
    }

    public static String getHeaders() {
        return Persona.getHeaders() +";CODIGO;CUIL";
    }
}
