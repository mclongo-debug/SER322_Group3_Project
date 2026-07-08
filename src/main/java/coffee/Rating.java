package coffee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Rating {
    private final Connection connection;
    private final Scanner scanner;

    public Rating(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void addRating() {
        System.out.print("Enter Rating ID: ");
        String ratingID = scanner.nextLine();

        System.out.print("Enter Comment: ");
        String comment = scanner.nextLine();

        System.out.print("Enter Date (YYYY-MM-DD): ");
        String dateText = scanner.nextLine();

        System.out.print("Enter Drink ID: ");
        String drinkID = scanner.nextLine();

        System.out.print("Enter Client ID: ");
        String clientID = scanner.nextLine();

        System.out.print("Enter Order ID: ");
        String orderID = scanner.nextLine();

        try {
            System.out.print("Enter Rating Value (1-5): ");
            int value = Integer.parseInt(scanner.nextLine());

            if (value < 1 || value > 5) {
                System.out.println("Rating value must be between 1 and 5.");
                return;
            }

            String sql = "INSERT INTO RATING (RatingID, Value, Comment, Date, DrinkID, ClientID, OrderID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, ratingID);
                pstmt.setInt(2, value);
                pstmt.setString(3, comment);
                pstmt.setDate(4, Date.valueOf(dateText));
                pstmt.setString(5, drinkID);
                pstmt.setString(6, clientID);
                pstmt.setString(7, orderID);

                int rows = pstmt.executeUpdate();
                System.out.println(rows > 0
                        ? "Rating added successfully."
                        : "Rating was not added.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating value. Please enter a whole number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }


    public void deleteRating() {
        System.out.print("Enter Rating ID to delete: ");
        String ratingID = scanner.nextLine();

        String sql = "DELETE FROM RATING WHERE RatingID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ratingID);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0
                    ? "Rating deleted successfully."
                    : "Rating not found.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void searchRating() {
        System.out.print("Enter Rating ID to search: ");
        String ratingID = scanner.nextLine();

        String sql = "SELECT * FROM RATING WHERE RatingID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ratingID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    printRating(rs);
                } else {
                    System.out.println("Rating not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void listRatings() {
        String sql = "SELECT r.*, d.Name AS DrinkName, c.Name AS ClientName "
                + "FROM RATING r "
                + "JOIN DRINK d ON r.DrinkID = d.DrinkID "
                + "JOIN CLIENTE c ON r.ClientID = c.ClientID "
                + "ORDER BY r.Date";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Rating List ---");
            boolean found = false;

            while (rs.next()) {
                found = true;
                printRating(rs);
                System.out.println("Drink: " + rs.getString("DrinkName"));
                System.out.println("Client: " + rs.getString("ClientName"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No ratings found.");
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private void printRating(ResultSet rs) throws SQLException {
        System.out.println("Rating ID: " + rs.getString("RatingID"));
        System.out.println("Value: " + rs.getInt("Value") + "/5");
        System.out.println("Comment: " + rs.getString("Comment"));
        System.out.println("Date: " + rs.getDate("Date"));
    }
}