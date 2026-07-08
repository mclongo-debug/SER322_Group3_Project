package coffee;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Database Connection Setup");

        System.out.print("Database URL: ");
        String url = scanner.nextLine();

        System.out.print("Database username: ");
        String user = scanner.nextLine();

        System.out.print("Database password: ");
        String password = scanner.nextLine();

        String driver = "com.mysql.cj.jdbc.Driver";

        Connection connection = DBConnection.getConnection(url, user, password, driver);

        if (connection == null) {
            System.out.println("Could not connect to database. Exiting application.");
            scanner.close();
            return;
        }

        Menu menu = new Menu(connection);
        menu.start();

        DBConnection.closeConnection();
        scanner.close();
    }
}
