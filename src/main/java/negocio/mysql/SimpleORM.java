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
            String [] sqlCommands = Files.readString(Paths.get("src/main/resources/migration.sql")).split(";");
            for (String command : sqlCommands) {
                try{
                    Statement stmt = connection.createStatement();
                    stmt.execute("USE ventas_bicicletas");
                    stmt.execute(command);
                }catch(SQLException e){
                    System.err.println("Error al ejecutar comando SQL: " + e.getMessage());
                    continue;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
