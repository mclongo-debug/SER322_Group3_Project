package coffee;

import java.sql.*;
import java.util.Scanner;

public class Cliente {
    private Connection connection;
    private Scanner scanner;

    public Cliente(Connection connection) {
        this.connection = connection;
        scanner = new Scanner(System.in);
    }

    public void addCliente() {
        System.out.print("Enter Client ID: ");
        String clientID = scanner.nextLine();

        System.out.print("Enter Client Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Member ID: ");
        String memberID = scanner.nextLine();

        System.out.print("Enter Preferred Roast: ");
        String preferredRoast = scanner.nextLine();

        System.out.print("Enter Preferred Drink: ");
        String preferredDrink = scanner.nextLine();

        try {
            String sql = "INSERT INTO CLIENTE (ClientID, Name, MemberID, PreferredRoast, PreferredDrink) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clientID);
            pstmt.setString(2, name);
            pstmt.setString(3, memberID);
            pstmt.setString(4, preferredRoast);
            pstmt.setString(5, preferredDrink);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Client added successfully.");
            }
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

public void updateCliente() {
    System.out.print("Enter Client ID to update: ");
    String clientID = scanner.nextLine();

    System.out.print("Enter new Name: ");
    String name = scanner.nextLine();

    System.out.print("Enter new Preferred Roast: ");
    String preferredRoast = scanner.nextLine();

    System.out.print("Enter new Preferred Drink: ");
    String preferredDrink = scanner.nextLine();

    try {
        String sql = "UPDATE CLIENTE SET Name = ?, PreferredRoast = ?, PreferredDrink = ? WHERE ClientID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, preferredRoast);
        pstmt.setString(3, preferredDrink);
        pstmt.setString(4, clientID);

        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Client updated successfully.");
        } else {
            System.out.println("Client not found.");
        }
        pstmt.close();

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    public void deleteCliente() {
        System.out.print("Enter Client ID to delete: ");
        String clientID = scanner.nextLine();

        try {
            String sql = "DELETE FROM CLIENTE WHERE ClientID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clientID);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Client deleted successfully.");
            } else {
                System.out.println("Client not found.");
            }
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void searchCliente() {
        System.out.print("Enter Client ID to search: ");
        String clientID = scanner.nextLine();

        try {
            String sql = "SELECT * FROM CLIENTE WHERE ClientID = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, clientID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Client ID: " + rs.getString("ClientID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Member ID: " + rs.getString("MemberID"));
                System.out.println("Preferred Roast: " + rs.getString("PreferredRoast"));
                System.out.println("Preferred Drink: " + rs.getString("PreferredDrink"));
            } else {
                System.out.println("Client not found.");
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void listClientes() {
        try {
            String sql = "SELECT * FROM CLIENTE";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Client List ---");
            while (rs.next()) {
                System.out.println("Client ID: " + rs.getString("ClientID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Member ID: " + rs.getString("MemberID"));
                System.out.println("Preferred Roast: " + rs.getString("PreferredRoast"));
                System.out.println("Preferred Drink: " + rs.getString("PreferredDrink"));
                System.out.println("-------------------------");
            }
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}
