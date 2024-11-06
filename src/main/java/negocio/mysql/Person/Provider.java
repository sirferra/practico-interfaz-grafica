package negocio.mysql.Person;

import java.util.Map;

import modelo.persona.Proveedor;
import negocio.mysql.Database;
import negocio.mysql.IMysqlEntity;

public class Provider implements IMysqlEntity {
    
    public static boolean create(Object obj) {
        Proveedor persona = (Proveedor) obj;
        Map<String,String> data = Map.of(
                "cuit", persona.getCuit(),
                "nombreFantasia", persona.getNombreFantasia(),
                "nombre", persona.getNombre(),
                "apellido", persona.getApellido(),
                "dni", String.valueOf(persona.getDni()),
                "telefono", persona.getTelefono(),
                "email", persona.getEmail()
        );
        return Database.getInstance().create("proveedor", data);
    }
    
    public static boolean update(Object oldObj, Object newObj) {
        int id =  Database.getInstance().getId("proveedor", "cuit", String.valueOf(((Proveedor) oldObj).getCuit()));
        return Provider.update(id, newObj);
    }

    public static boolean update(int id, Object newObj) {
        Proveedor persona = (Proveedor) newObj;
        Map<String,String> data = Map.of(
                "cuit", persona.getCuit(),
                "nombreFantasia", persona.getNombreFantasia(),
                "nombre", persona.getNombre(),
                "apellido", persona.getApellido(),
                "dni", String.valueOf(persona.getDni()),
                "telefono", persona.getTelefono(),
                "email", persona.getEmail()
        );
        return Database.getInstance().update("proveedor", data, id);
    }
    
    public static boolean delete(int id) {
        return Database.getInstance().delete("proveedor", id);
    }

    public static Proveedor get(int id) {
        return (Proveedor) Database.getInstance().get("proveedor", id, Proveedor.class);
    }

    public static Proveedor get(String cuit) {
        try {
            int id = Database.getInstance().getId("proveedor", "cuit", cuit);
            return Provider.get(id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public static int getId(String cuit) {
        return Database.getInstance().getId("proveedor", "cuit", cuit);
    }

    public static Proveedor[] getAll() {
        try{
            return (Proveedor[]) Database.getInstance().getAll( Proveedor[].class);
        } catch (Exception e) {
            return null;
        }
    }
}
