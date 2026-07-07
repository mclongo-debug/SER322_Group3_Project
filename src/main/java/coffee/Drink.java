package coffee;

import java.sql.*;
import java.util.Scanner;

public class Drink {
    private Connection connection;
    private Scanner scanner;

    public Drink(Connection connection){
        this.connection = connection;
        scanner = new Scanner(System.in);
    }

    public void addDrink(){
        System.out.print("Enter Drink ID: ");
        String drinkID = scanner.nextLine();

        System.out.print("Enter Drink Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Drink Type: ");
        String type = scanner.nextLine();

        System.out.print("Enter Flavor: ");
        String flavor = scanner.nextLine();

        try {
            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            String sql = "INSERT INTO DRINK (DrinkID, Name, Type, Flavor, Price) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, drinkID);
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            pstmt.setString(4, flavor);
            pstmt.setDouble(5, price);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Drink added successfully.");
            }

            pstmt.close();

        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a number.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateDrink(){
        System.out.print("Enter Drink ID to update: ");
        String drinkID = scanner.nextLine();

        System.out.print("Enter new Drink Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new Drink Type: ");
        String type = scanner.nextLine();

        System.out.print("Enter new Flavor: ");
        String flavor = scanner.nextLine();

        try {
            System.out.print("Enter new Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            String sql = "UPDATE DRINK SET Name = ?, Type = ?, Flavor = ?, Price = ? WHERE DrinkID = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, type);
            pstmt.setString(3, flavor);
            pstmt.setDouble(4, price);
            pstmt.setString(5, drinkID);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Drink updated successfully.");
            } else {
                System.out.println("Drink not found.");
            }

            pstmt.close();

        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a number.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void deleteDrink(){
        System.out.print("Enter Drink ID to delete: ");
        String drinkID = scanner.nextLine();

        try {
            String sql = "DELETE FROM DRINK WHERE DrinkID = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, drinkID);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Drink deleted successfully.");
            } else {
                System.out.println("Drink not found.");
            }

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

    }

    public void searchDrink(){
        System.out.print("Enter Drink ID to search: ");
        String drinkID = scanner.nextLine();

        try {
            String sql = "SELECT * FROM DRINK WHERE DrinkID = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, drinkID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Drink ID: " + rs.getString("DrinkID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Type: " + rs.getString("Type"));
                System.out.println("Flavor: " + rs.getString("Flavor"));
                System.out.println("Price: $" + rs.getDouble("Price"));
            } else {
                System.out.println("Drink not found.");
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

    }

    public void listDrinks(){
        try {
            String sql = "SELECT * FROM DRINK";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- Drink List ---");

            while (rs.next()) {
                System.out.println("Drink ID: " + rs.getString("DrinkID"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Type: " + rs.getString("Type"));
                System.out.println("Flavor: " + rs.getString("Flavor"));
                System.out.println("Price: $" + rs.getDouble("Price"));
                System.out.println("-------------------------");

            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

    }

}
