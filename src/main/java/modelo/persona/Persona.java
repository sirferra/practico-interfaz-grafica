
package modelo.persona;

public abstract class Persona {
    
    private String nombre;
    private String apellido;
    private int dni;
    private String telefono;
    private String email;

    public Persona(){
        super();
    }
    
    public Persona(String nombre, String apellido, int dni, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
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
    public abstract String generarCodigo();
}
