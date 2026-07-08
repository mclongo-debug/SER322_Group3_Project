package coffee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection - Manages the database connection for CoffeeShop_Group3
 * Connection pattern adapted from JDBC Lab examples
 */
public class DBConnection {

    private static Connection conn = null;

    /**
     * Opens and returns a connection to the database
     * @param url database URL
     * @param user database username
     * @param password database password
     * @param driver JDBC driver class name
     * @return Connection object or null if failed
     */
    public static Connection getConnection(String url, String user,
                                           String password, String driver) {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to CoffeeShop successfully!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Closes the database connection
     */
    public static void closeConnection() {
        try {
            if (conn != null) conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}