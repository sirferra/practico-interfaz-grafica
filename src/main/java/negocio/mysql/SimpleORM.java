package negocio.mysql;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleORM {

    private Connection connection;

    public SimpleORM(Connection connection) {
        this.connection = connection;
    }

    public <T> boolean create(T entity) throws Exception {
        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);

        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        Map<String, Object> columns = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                columns.put(column.name(), field.get(entity));
            }
        }

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
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            Column column = field.getAnnotation(Column.class);
                            field.set(entity, rs.getObject(column.name()));
                        }
                    }
                    return entity;
                }
            }
        }
        return null;
    }

    public <T> boolean update(T entity,int id) throws Exception {
        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        Map<String, Object> columns = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                if (!column.name().equals("id")) {
                    columns.put(column.name(), field.get(entity));
                }
            }
        }

        if (id <=0)
            throw new RuntimeException("El ID no fue especificado");

        String setClause = String.join(",", columns.keySet().stream().map(k -> k + " = ?").toArray(String[]::new));
        String sql = "UPDATE " + table.name() + " SET " + setClause + " WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : columns.values()) {
                stmt.setObject(index++, value);
            }
            stmt.setInt(index, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Object getIdFieldValue(Object entity) throws Exception {
        Class<?> clazz = entity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);  // Permitir el acceso a campos privados
                return field.get(entity);   // Obtener el valor del campo marcado con @Id
            }
        }
        throw new RuntimeException("No se encontró el campo con la anotación @Id en la clase " + clazz.getSimpleName());
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
            return tableAnnotation.name();  // Retorna el nombre de la tabla
        } else {
            throw new RuntimeException("La clase " + clazz.getSimpleName() + " no tiene la anotación @Table");
        }
    }


    public <T> T findByField(Class<T> clazz, String fieldName, Object value) throws Exception {
        Table table = clazz.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");

        // Crear la consulta SQL dinámica
        String sql = "SELECT * FROM " + table.name() + " WHERE " + fieldName + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, value);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear una nueva instancia de la clase y asignar los valores
                    T entity = clazz.getDeclaredConstructor().newInstance();
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Column.class)) {
                            field.setAccessible(true);
                            Column column = field.getAnnotation(Column.class);
                            field.set(entity, rs.getObject(column.name()));
                        }
                    }
                    return entity;
                }
            }
        }
        return null;
    }

    public <T> String generateCsv(Class<T> type) throws Exception {
        // Obtener la anotación de la tabla
        Table table = type.getAnnotation(Table.class);
        if (table == null)
            throw new RuntimeException("La clase no está anotada con @Table");
    
        // Obtener los nombres de las columnas y crear el encabezado del CSV
        List<String> columnNames = new ArrayList<>();
        StringBuilder header = new StringBuilder();
    
        // Iterar sobre los campos de la clase y sus superclases
        Class<?> currentClass = type;
        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    columnNames.add(column.name());
                    if (header.length() > 0)
                        header.append(";");
                    header.append(column.name().toUpperCase()); // Agregar el nombre de la columna al encabezado
                }
            }
            currentClass = currentClass.getSuperclass();  // Moverse a la superclase
        }
    
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
                    csv.append(rs.getString(columnName));
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
    
}
