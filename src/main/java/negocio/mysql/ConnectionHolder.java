package negocio.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionHolder {
    private static final String CONNECTION_STRING = "jdbc:mysql://root:1234@localhost:33006";
    private static final Connection connection;
    private static final SimpleORM orm;
    private ConnectionHolder() {
    }

    static {
        try {
            // System.out.println("Obteniendo database connection...");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            orm = new SimpleORM(connection);
            // System.out.println("Database connection obtenida.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    public static SimpleORM getInstance() {
        return orm;
    }
}

