package negocio.mysql;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import modelo.pedido.DetallePedido;
import modelo.pedido.Pedido;
import modelo.persona.Cliente;
import modelo.persona.Proveedor;
import modelo.persona.Vendedor;
import modelo.producto.Categoria;
import modelo.producto.Marca;
import modelo.producto.Modelo;
import modelo.producto.Producto;
import negocio.Storage.IStorage;

public class Database implements IStorage {
    private static Database instance;
    final private String connectionString = "jdbc:mysql://root:1234@localhost:3307/ventas_bicicletas";
    public static Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Map<Class<?>, String[]> csvConfig;

    private Database() {
        try {
            Database.connection = DriverManager.getConnection(connectionString);
            Database.createDBifNotExists();
            Database.createTablesIfNotExists();
            this.csvConfig = new HashMap<>();
            csvConfig.put(Cliente.class, new String[] { "SELECT * FROM clientes", "NOMBRE;APELLIDO;DNI;TELEFONO;EMAIL",
                    "nombre", "apellido", "dni", "telefono", "email" });
            csvConfig.put(Vendedor.class,
                    new String[] { "SELECT * FROM vendedores", "SUCURSAL;NOMBRE;APELLIDO;DNI;TELEFONO;EMAIL",
                            "sucursal", "nombre", "apellido", "dni", "telefono", "email" });
            csvConfig.put(Proveedor.class,
                    new String[] { "SELECT * FROM proveedores", "CUIL;NOMBRE;APELLIDO;DNI;TELEFONO;EMAIL", "cuit",
                            "nombreFantasia", "nombre", "apellido", "dni", "telefono", "email" });
            csvConfig.put(Pedido.class, new String[] { "SELECT * FROM pedidos", "ID;CLIENTE;FECHA;TOTAL", "id",
                    "cliente", "fecha", "total" });
            csvConfig.put(Producto.class, new String[] { "SELECT * FROM productos", "ID;NOMBRE;CATEGORIA;MARCA;PRECIO",
                    "id", "nombre", "categoria", "marca", "precio" });
            csvConfig.put(DetallePedido.class,
                    new String[] { "SELECT * FROM detalles_pedidos", "PEDIDO;PRODUCTO;CANTIDAD;PRECIO;DESCUENTO",
                            "pedido", "producto", "cantidad", "precio", "descuento" });
            csvConfig.put(Marca.class, new String[] { "SELECT * FROM marcas", "ID;NOMBRE", "id", "nombre" });
            csvConfig.put(Modelo.class, new String[] { "SELECT * FROM modelos", "ID;NOMBRE", "id", "nombre" });
            csvConfig.put(Categoria.class, new String[] { "SELECT * FROM categorias", "ID;NOMBRE", "id", "nombre" });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static void createDBifNotExists() {
        try {
            connection.createStatement().execute("CREATE DATABASE IF NOT EXISTS ventas_bicicletas");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTablesIfNotExists() {
        try {
            connection.createStatement().execute("CREATE TABLE IF NOT EXISTS clientes (id INT AUTO_INCREMENT PRIMARY KEY, cuit VARCHAR(11), nombre VARCHAR(255), apellido VARCHAR(255), dni INT, telefono VARCHAR(20), email VARCHAR(255))");
            // Añadir la creación de las demás tablas
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
public boolean create(String table, Map<String, String> values) {
        String columns = String.join(",", values.keySet());
        String placeholders = String.join(",", values.keySet().stream().map(k -> "?").toArray(String[]::new));
        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (String key : values.keySet()) {
                stmt.setString(index++, values.get(key));
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    public boolean update(String table, Map<String, String> values, int id) {
        String setClause = String.join(",", values.keySet().stream().map(key -> key + " = ?").toArray(String[]::new));
        String sql = "UPDATE " + table + " SET " + setClause + " WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (String key : values.keySet()) {
                stmt.setString(index++, values.get(key));
            }
            stmt.setInt(index, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(String table, Object objId) {
        String sql = "DELETE FROM " + table + " WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(objId.toString()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }

    public boolean exists(String table, Object objId) {
        String sql = "SELECT 1 FROM " + table + " WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(objId.toString()));
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Retorna true si hay un resultado, false si no
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia: " + e.getMessage());
            return false;
        }
    }

    public Object get(String table, int id, Class<?> type) {
        String sql = "SELECT * FROM " + table + " WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear una nueva instancia de la clase tipo
                    Object instance = type.getDeclaredConstructor().newInstance();
    
                    // Iterar sobre los campos declarados en la clase
                    for (Field field : type.getDeclaredFields()) {
                        field.setAccessible(true); // Permitir acceso a campos privados
    
                        // Obtener el valor de la columna usando el nombre del campo
                        try {
                            Object value = rs.getObject(field.getName());
                            field.set(instance, value);  // Asignar el valor al campo de la instancia
                        } catch (SQLException e) {
                            System.err.println("Error al obtener el valor del campo: " + field.getName() + " - " + e.getMessage());
                        }
                    }
                    return instance;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al crear instancia: " + e.getMessage());
        }
        return null;
    }

    public int getId(String table, String field, String value) {
        String sql = "SELECT id FROM " + table + " WHERE " + field + " = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");  // Obtener el id
                } else {
                    System.err.println("No se encontró un registro con el valor especificado");
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener id: " + e.getMessage());
            return -1;
        }
    }

    public Object[] getAll(Class<?> type) {
        try {
            String table = ((IMysqlEntity) type.getDeclaredConstructor().newInstance()).table;
            String sql = "SELECT * FROM " + table;

            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                rs.last();  // Mover a la última fila para obtener la cantidad de resultados
                int rowCount = rs.getRow();
                rs.beforeFirst();  // Volver al inicio para iterar

                Object[] result = new Object[rowCount];
                int i = 0;
                while (rs.next()) {
                    result[i++] = rs.getObject(1, type);
                }
                return result;
            }
        } catch (Exception e) {
            System.err.println("Error al obtener todos los datos: " + e.getMessage());
            return null;
        }
    }

    @Override
    public <T> String generateCsv(Class<T> type) {

        String[] params = csvConfig.get(type);
        if (params == null) {
            return null;
        }

        String query = params[0];
        String header = params[1];
        String[] columns = new String[params.length - 2];
        System.arraycopy(params, 2, columns, 0, columns.length);

        // Generar CSV
        try (ResultSet rs = Database.connection.createStatement().executeQuery(query)) {
            StringBuilder csv = new StringBuilder();
            csv.append(header).append("\n");

            while (rs.next()) {
                for (int i = 0; i < columns.length; i++) {
                    csv.append(rs.getString(columns[i]));
                    if (i < columns.length - 1)
                        csv.append(";");
                }
                csv.append("\n");
            }

            return csv.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
