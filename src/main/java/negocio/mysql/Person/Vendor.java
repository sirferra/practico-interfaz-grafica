package negocio.mysql.Person;

import java.util.Map;
import modelo.persona.Vendedor;
import negocio.mysql.Database;
import negocio.mysql.IMysqlEntity;

public class Vendor implements IMysqlEntity {
    public static final String table = "vendedores";
    public static boolean create(Object obj) {
        Vendedor persona = (Vendedor) obj;
        Map<String,String> data = Map.of(
                "sucursal", persona.getSucursal(),
                "nombre", persona.getNombre(),
                "apellido", persona.getApellido(),
                "dni", String.valueOf(persona.getDni()),
                "telefono", persona.getTelefono(),
                "email", persona.getEmail()
        );
        return Database.getInstance().create("vendedores", data);
    }
    public static boolean update(int id, Object newObj) {
        Vendedor persona = (Vendedor) newObj;
        Map<String,String> data = Map.of(
                "sucursal", persona.getSucursal(),
                "nombre", persona.getNombre(),
                "apellido", persona.getApellido(),
                "dni", String.valueOf(persona.getDni()),
                "telefono", persona.getTelefono(),
                "email", persona.getEmail()
        );
        return Database.getInstance().update("vendedores", data, id);
    }
    public static boolean delete(int id) {
        return Database.getInstance().delete("vendedores", id);
    }
    public static Vendedor get(int id) {
        return (Vendedor) Database.getInstance().get("vendedores", id, Vendedor.class);
    }
    public static Vendedor[] getAll() {
        try{
            return (Vendedor[]) Database.getInstance().getAll(Vendedor.class);
        }catch(Exception e){
            return null;
        }
    }
}
