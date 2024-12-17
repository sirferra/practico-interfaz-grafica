package negocio.mysql;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimpleORM {

    private Connection connection;

    public SimpleORM(Connection connection) {
        this.connection = connection;
        try{
            System.out.println("Iniciando Database...");
            checkForDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try{
            connection.setCatalog("ventas_bicicletas");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public <T> boolean create(T entity) throws Exception {
        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);

        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        Map<String, Object> columns = new HashMap<>();

        Class<?> currentClass = clazz;
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    columns.put(column.name(), field.get(entity));
                }
            }
            currentClass = currentClass.getSuperclass(); // Moverse a la superclase
        }
        // remove id from columns
        columns.remove("id");

        String sql = "INSERT INTO " + table.name() + " (" + String.join(",", columns.keySet()) + ") VALUES ("
                + String.join(",", columns.keySet().stream().map(k -> "?").toArray(String[]::new)) + ")";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : columns.values()) {
                stmt.setObject(index++, value);
            }
            return stmt.executeUpdate() > 0;
        }
    }
    
    public <T> T findById(Class<T> clazz, int id) throws Exception {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        String sql = "SELECT * FROM " + table.name() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    Class<?> currentClass = clazz;
                    while (currentClass != null) {
                        for (Field field : currentClass.getDeclaredFields()) {
                            if (field.isAnnotationPresent(Column.class)) {
                                field.setAccessible(true);
                                Column column = field.getAnnotation(Column.class);
                                field.set(entity, rs.getObject(column.name()));
                            }
                        }
                        currentClass = currentClass.getSuperclass();
                    }
                    return entity;
                }
            }
        }
        return null;
    }

    public <T> boolean update(T entity, Map<String, String> values, int id) throws Exception {
        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        if (id <= 0)
            throw new RuntimeException("El ID no fue especificado");

        String setClause = String.join(",", values.keySet().stream().map(k -> k + " = ?").toArray(String[]::new));
        String sql = "UPDATE " + table.name() + " SET " + setClause + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : values.values()) {
                stmt.setObject(index++, value);
            }
            stmt.setInt(index, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Object getIdFieldValue(Object entity, String query, String column) throws Exception {
        String sql = "SELECT id FROM " + entity.getClass().getAnnotation(Table.class).name() + " WHERE " + column
                + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, query);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getObject("id");
                }
            }
        }
        throw new RuntimeException(
                "No se encontró el campo con la anotación @Id en la clase " + entity.getClass().getSimpleName());
    }

    public <T> boolean delete(Class<T> clazz, int id) throws Exception {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        String sql = "DELETE FROM " + table.name() + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public static <T> String getTableName(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            Table tableAnnotation = clazz.getAnnotation(Table.class);
            return tableAnnotation.name(); // Retorna el nombre de la tabla
        } else {
            throw new RuntimeException("La clase " + clazz.getSimpleName() + " no tiene la anotación @Table");
        }
    }

    public <T> T findByField(Class<T> clazz, String fieldName, Object value) throws Exception {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        String sql = "SELECT * FROM " + table.name() + " WHERE " + fieldName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    Class<?> currentClass = clazz;
                    while (currentClass != null) {
                        for (Field field : currentClass.getDeclaredFields()) {
                            if (field.isAnnotationPresent(Column.class)) {
                                field.setAccessible(true);
                                Column column = field.getAnnotation(Column.class);
                                field.set(entity, rs.getObject(column.name()));
                            }
                        }
                        currentClass = currentClass.getSuperclass();
                    }
                    return entity;
                }
            }
        }
        return null;
    }

    
    public <T> int count(Class<T> clazz) throws Exception {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        String sql = "SELECT COUNT(*) as cantidad FROM " + table.name();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cantidad");
                }
            }
        }
        return 0;
    }
    public void addHeaders(Class<?> currentClass, List<String> columnNames, StringBuilder header) {
        for (Field field : currentClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnNames.add(column.name());
                if (header.length() > 0)
                    header.append(";");
                header.append(column.name().toUpperCase());                    
            }
        }
    }

    public <T> String generateCsv(Class<T> type) throws Exception {
        // Obtener la anotación de la tabla
        Table table = type.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        // Obtener los nombres de las columnas y crear el encabezado del CSV
        List<String> columnNames = new ArrayList<>();
        StringBuilder header = new StringBuilder();

        // Iterar sobre los campos de la clase y sus superclases (reviso si es null por si acaso)
        if(type.getSuperclass() != null)
            this.addHeaders(type.getSuperclass(), columnNames, header);
        
        this.addHeaders(type, columnNames, header);

        // Crear la consulta SQL dinámica
        String sql = "SELECT * FROM " + table.name();
        StringBuilder csv = new StringBuilder();
        csv.append(header).append("\n");

        // Ejecutar la consulta y construir el CSV
        try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                for (int i = 0; i < columnNames.size(); i++) {
                    String columnName = columnNames.get(i);
                    String value = rs.getString(columnName);
                    csv.append(value == null || value.isEmpty() ? "-" : value);
                    if (i < columnNames.size() - 1) {
                        csv.append(";");
                    }
                }
                csv.append("\n");
            }
        } catch (SQLException e) {
            System.err.println("Error al generar CSV: " + e.getMessage());
            return null;
        }

        return csv.toString();
    }

    public void checkForDatabase() throws SQLException{
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet rs = metaData.getCatalogs();
        boolean databaseExists = false;
        while (rs.next()) {
            String databaseName = rs.getString("TABLE_CAT");
            if (databaseName.equals("ventas_bicicletas")) {
                databaseExists = true;
                break;
            }
        }
        if(!databaseExists){
            try {
                System.out.println("Creando Base De datos y tablas...");
                Statement stmt = connection.createStatement();
                stmt.execute("CREATE DATABASE IF NOT EXISTS ventas_bicicletas");
            } catch (SQLException e) {
                System.err.println("Error al crear base de datos y tablas: " + e.getMessage());
            } 
        }
        createTables();
    }

    public void createTables() throws SQLException {
        try{
            Statement stmt = connection.createStatement();
            stmt.execute("USE ventas_bicicletas");
            String[] sqlCommands = Files.readString(Paths.get("src/main/resources/dump.sql")).split(";");
            for (String command : sqlCommands) {
                try{
                    stmt.execute(command);
                }catch(SQLException e){
                    continue;
                }
            }
        }catch (IOException e) {
            System.err.println("Error al leer archivo SQL: " + e.getMessage());
        }
    }

    public void seed(){
        try{
            System.out.println("Llenando base de datos...");
            Statement stmt = connection.createStatement();
            stmt.execute("USE ventas_bicicletas");
            stmt.execute("DROP DATABASE IF EXISTS ventas_bicicletas");
            stmt.execute("CREATE DATABASE IF NOT EXISTS ventas_bicicletas");
            createTables();
            stmt.execute("USE ventas_bicicletas");
            String [] sqlCommands = Files.readString(Paths.get("src/main/resources/migration.sql")).split(";");
            for (String command : sqlCommands) {
                try{
                    stmt.execute(command);
                }catch(SQLException e){
                    System.err.println("Error al ejecutar comando SQL: " + e.getMessage());
                    continue;
                }
            }
        }catch (IOException|SQLException e) {
            e.printStackTrace();
        } 
    }

    public Object[][] getAll(Class<?> class1, String _sql) {
        try {
            String sql= "";
            if(_sql != null){
                sql = _sql;
            }else{
                sql = "SELECT * FROM " + class1.getAnnotation(Table.class).name();
            }
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<Object[]> data = new ArrayList<>();
            
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                data.add(row);
            }
            
            // Crear el arreglo Object[][] manualmente
            Object[][] result = new Object[data.size()][columnCount];
            for (int i = 0; i < data.size(); i++) {
                result[i] = data.get(i);
            }
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<?> getAll(Class<?> clazz) {
        try {
            String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            List<Object> data = new ArrayList<>();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                Class<?> currentClass = clazz;
                while (currentClass != null) {
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        try {
                            Field field = currentClass.getDeclaredField(columnName);
                            field.setAccessible(true);
                            field.set(instance, rs.getObject(i));
                        } catch (NoSuchFieldException e) {
                            // Continue searching in the superclass
                        }
                    }
                    currentClass = currentClass.getSuperclass();
                }
                data.add(instance);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTotalPedido(int idPedido) {
        try {
            String sql = "Select total from pedido where id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return String.valueOf(rs.getDouble("total"));
            }
            return "0";
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }
    
    public String getProductoMasVendido() {
        try {
            String sql = "SELECT p.nombre, SUM(dp.cantidad) as cantidad FROM detalle_pedido dp INNER JOIN producto p ON dp.producto_id = p.id GROUP BY p.nombre ORDER BY cantidad DESC LIMIT 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre")+ ":" + rs.getInt("cantidad");
            }
            return "-";
        } catch (Exception e) {
            e.printStackTrace();
            return "-";
        }
    }

    public void saveSale(String clienteId, String vendedorId, String fecha, List<Object[]> productosFromTable) throws SQLException {
        // Insert new sale into 'pedido' table
        String insertPedidoSql = "INSERT INTO pedido (cliente_id, vendedor_id, fecha, total, estado) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pedidoStmt = connection.prepareStatement(insertPedidoSql, Statement.RETURN_GENERATED_KEYS);
        pedidoStmt.setInt(1, Integer.parseInt(clienteId));
        pedidoStmt.setInt(2, Integer.parseInt(vendedorId));
        pedidoStmt.setDate(3, Date.valueOf(fecha));
        
        double total = productosFromTable.stream().mapToDouble(p -> (double) p[3]).sum();
        pedidoStmt.setDouble(4, total);
        pedidoStmt.setString(5, "PREPARACION");
        
        pedidoStmt.executeUpdate();
        ResultSet generatedKeys = pedidoStmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            int pedidoId = generatedKeys.getInt(1);
            
            // Insert each product into 'detalle_pedido' table
            String insertDetalleSql = "INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, precio) VALUES (?, ?, ?, ?)";
            PreparedStatement detalleStmt = connection.prepareStatement(insertDetalleSql);
            for (Object[] producto : productosFromTable) {
                int productoId =(int) producto[0]; 
                int cantidad = (int) producto[2];
                double precio = (double) producto[3];
                
                detalleStmt.setInt(1, pedidoId);
                detalleStmt.setInt(2, productoId);
                detalleStmt.setInt(3, cantidad);
                detalleStmt.setDouble(4, precio);
                detalleStmt.addBatch();
            }
            detalleStmt.executeBatch();
        }
    
    }

    public void execute(String sql) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, Object>> executeQuery(String sql) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
    
            while (rs.next()) {
                HashMap<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), rs.getObject(i));
                }
                result.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
