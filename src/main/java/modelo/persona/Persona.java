
package modelo.persona;

import java.util.HashMap;
import java.util.Map;

import negocio.mysql.Column;
import negocio.mysql.Id;

public abstract class Persona {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "dni")
    private int dni;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "email")
    private String email;

    public Persona(){
        super();
    }
    
    public Persona(int id, String nombre, String apellido, int dni, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }
    public Persona(String nombre, String apellido, int dni, String telefono, String email) {
        this.id = -1;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("apellido", apellido);
        map.put("dni", String.valueOf(dni));
        map.put("telefono", telefono);
        map.put("email", email);
        return map;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString(){
        return  this.nombre+ ";"
                +this.apellido+";"
                +this.dni+ ";"
                +this.telefono+";"
                +this.email;
    }

    /**
     * @return String with headers of the CSV
     */
    public static String getHeaders(){
        return "NOMBRE;APELLIDO;DNI;TELEFONO;EMAIL";
    }
}
