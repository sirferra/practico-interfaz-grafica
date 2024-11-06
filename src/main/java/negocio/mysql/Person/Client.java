package negocio.mysql.Person;

import java.util.Map;

import modelo.persona.Cliente;
import negocio.mysql.Database;
import negocio.mysql.IMysqlEntity;

public class Client implements IMysqlEntity{

    
    public static boolean create(Object obj) {
        Cliente persona = (Cliente) obj;
        Map<String,String> data = Map.of(
                "cuil", persona.getCuil(),
                "nombre", persona.getNombre(),
                "apellido", persona.getApellido(),
                "dni", String.valueOf(persona.getDni()),
                "telefono", persona.getTelefono(),
                "email", persona.getEmail()
        );
        return Database.getInstance().create("clientes", data);
    }

    public static boolean update(Object oldObj, Object newObj) {
        Cliente persona = (Cliente) newObj;
        Cliente oldPersona = (Cliente) oldObj;
        Map<String,String> data = Map.of(
                "cuil", persona.getCuil(),
                "nombre", persona.getNombre(),
                "apellido", persona.getApellido(),
                "dni", String.valueOf(persona.getDni()),
                "telefono", persona.getTelefono(),
                "email", persona.getEmail()
        );
        int id = Database.getInstance().getId("clientes", "dni", String.valueOf(oldPersona.getDni()));
        return Database.getInstance().update("clientes", data, id);
    }

    public static boolean update(int id, Object newObj) {
        Cliente persona = (Cliente) newObj;
        Map<String,String> data = Map.of(
                "cuil", persona.getCuil(),
                "nombre", persona.getNombre(),
                "apellido", persona.getApellido(),
                "dni", String.valueOf(persona.getDni()),
                "telefono", persona.getTelefono(),
                "email", persona.getEmail()
        );
        return Database.getInstance().update("clientes", data, id);
    }

    public static boolean delete(Object obj) {
        Cliente persona = (Cliente) obj;
        try{
            int id = Database.getInstance().getId("clientes", "dni", String.valueOf(persona.getDni()));
            Database.getInstance().delete("clientes", id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static boolean exists(Object obj) {
        Cliente persona = (Cliente) obj;
        try{
            int id = Database.getInstance().getId("clientes", "dni", String.valueOf(persona.getDni()));
            return id > 0;
        }catch(Exception e){
            return false;
        }
    }

    public Object get(int id) throws Exception {
        try{
            return Database.getInstance().get("clientes", id, Cliente.class);
        }catch(Exception e){
            return null;
        }
    }

    public Object get(String dni) throws Exception {
        try{
            return Database.getInstance().getId("clientes", "dni", dni);
        }catch(Exception e){
            return null;
        }
    }

    public int getId(String something) throws Exception {
        try{
            return Database.getInstance().getId("clientes", "dni", something);
        }catch(Exception e){
            return -1;
        }
    }

    public Object getAll() {
        try {
            return Database.getInstance().getAll(Cliente.class);
        } catch (Exception e) {
            return null;
        }
    }
    
}
