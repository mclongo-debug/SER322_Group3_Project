package coffee;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Order {
    private final Connection connection;
    private final Scanner scanner;

    public Order(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void addOrder() {
        System.out.print("Enter Order ID: ");
        String orderID = scanner.nextLine();

        System.out.print("Enter Order Date (YYYY-MM-DD): ");
        String dateText = scanner.nextLine();

        System.out.print("Enter Client ID: ");
        String clientID = scanner.nextLine();

        String sql = "INSERT INTO DRINK_ORDER (OrderID, Date, ClientID) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, orderID);
            pstmt.setDate(2, Date.valueOf(dateText));
            pstmt.setString(3, clientID);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0
                    ? "Order added successfully."
                    : "Order was not added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void addDrinkToOrder() {
        System.out.print("Enter Order ID: ");
        String orderID = scanner.nextLine();

        System.out.print("Enter Drink ID: ");
        String drinkID = scanner.nextLine();

        try {
            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
                return;
            }

            String sql = "INSERT INTO CONTAINS (OrderID, DrinkID, Quantity) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, orderID);
                pstmt.setString(2, drinkID);
                pstmt.setInt(3, quantity);

                int rows = pstmt.executeUpdate();
                System.out.println(rows > 0
                        ? "Drink added to order successfully."
                        : "Drink was not added to the order.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a whole number.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void updateOrder() {
        System.out.print("Enter Order ID to update: ");
        String orderID = scanner.nextLine();

        System.out.print("Enter new Order Date (YYYY-MM-DD): ");
        String dateText = scanner.nextLine();

        System.out.print("Enter new Client ID: ");
        String clientID = scanner.nextLine();

        String sql = "UPDATE DRINK_ORDER SET Date = ?, ClientID = ? WHERE OrderID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(dateText));
            pstmt.setString(2, clientID);
            pstmt.setString(3, orderID);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0
                    ? "Order updated successfully."
                    : "Order not found.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void updateDrinkQuantity() {
        System.out.print("Enter Order ID: ");
        String orderID = scanner.nextLine();

        System.out.print("Enter Drink ID: ");
        String drinkID = scanner.nextLine();

        try {
            System.out.print("Enter new Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
                return;
            }

            String sql = "UPDATE CONTAINS SET Quantity = ? WHERE OrderID = ? AND DrinkID = ?";

            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, quantity);
                pstmt.setString(2, orderID);
                pstmt.setString(3, drinkID);

                int rows = pstmt.executeUpdate();
                System.out.println(rows > 0
                        ? "Order quantity updated successfully."
                        : "Order/drink combination not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a whole number.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void deleteOrder() {
        System.out.print("Enter Order ID to delete: ");
        String orderID = scanner.nextLine();

        String deleteRatings = "DELETE FROM RATING WHERE OrderID = ?";
        String deleteContains = "DELETE FROM CONTAINS WHERE OrderID = ?";
        String deleteOrder = "DELETE FROM DRINK_ORDER WHERE OrderID = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement ratingStmt = connection.prepareStatement(deleteRatings);
                 PreparedStatement containsStmt = connection.prepareStatement(deleteContains);
                 PreparedStatement orderStmt = connection.prepareStatement(deleteOrder)) {

                ratingStmt.setString(1, orderID);
                ratingStmt.executeUpdate();

                containsStmt.setString(1, orderID);
                containsStmt.executeUpdate();

                orderStmt.setString(1, orderID);
                int rows = orderStmt.executeUpdate();

                if (rows > 0) {
                    connection.commit();
                    System.out.println("Order deleted successfully.");
                } else {
                    connection.rollback();
                    System.out.println("Order not found.");
                }
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void removeDrinkFromOrder() {
        System.out.print("Enter Order ID: ");
        String orderID = scanner.nextLine();

        System.out.print("Enter Drink ID to remove: ");
        String drinkID = scanner.nextLine();

        String sql = "DELETE FROM CONTAINS WHERE OrderID = ? AND DrinkID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, orderID);
            pstmt.setString(2, drinkID);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0
                    ? "Drink removed from order successfully."
                    : "Order/drink combination not found.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void searchOrder() {
        System.out.print("Enter Order ID to search: ");
        String orderID = scanner.nextLine();

        String orderSql = "SELECT o.OrderID, o.Date, o.ClientID, c.Name AS ClientName "
                + "FROM DRINK_ORDER o "
                + "JOIN CLIENTE c ON o.ClientID = c.ClientID "
                + "WHERE o.OrderID = ?";

        String itemSql = "SELECT d.DrinkID, d.Name, d.Price, ct.Quantity, "
                + "(d.Price * ct.Quantity) AS LineTotal "
                + "FROM CONTAINS ct "
                + "JOIN DRINK d ON ct.DrinkID = d.DrinkID "
                + "WHERE ct.OrderID = ? "
                + "ORDER BY d.Name";

        try (PreparedStatement orderStmt = connection.prepareStatement(orderSql);
             PreparedStatement itemStmt = connection.prepareStatement(itemSql)) {

            orderStmt.setString(1, orderID);

            try (ResultSet orderRs = orderStmt.executeQuery()) {
                if (!orderRs.next()) {
                    System.out.println("Order not found.");
                    return;
                }

                System.out.println("Order ID: " + orderRs.getString("OrderID"));
                System.out.println("Date: " + orderRs.getDate("Date"));
                System.out.println("Client ID: " + orderRs.getString("ClientID"));
                System.out.println("Client Name: " + orderRs.getString("ClientName"));
            }

            itemStmt.setString(1, orderID);

            try (ResultSet itemRs = itemStmt.executeQuery()) {
                BigDecimal total = BigDecimal.ZERO;
                boolean foundItem = false;

                System.out.println("--- Order Contents ---");
                while (itemRs.next()) {
                    foundItem = true;
                    BigDecimal lineTotal = itemRs.getBigDecimal("LineTotal");
                    total = total.add(lineTotal);

                    System.out.println("Drink ID: " + itemRs.getString("DrinkID"));
                    System.out.println("Name: " + itemRs.getString("Name"));
                    System.out.println("Price: $" + itemRs.getBigDecimal("Price"));
                    System.out.println("Quantity: " + itemRs.getInt("Quantity"));
                    System.out.println("Line Total: $" + lineTotal);
                    System.out.println("-------------------------");
                }

                if (!foundItem) {
                    System.out.println("This order has no drinks.");
                }

                System.out.println("Order Total: $" + total);
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void listOrders() {
        String sql = "SELECT o.OrderID, o.Date, o.ClientID, c.Name AS ClientName, "
                + "COALESCE(SUM(ct.Quantity), 0) AS TotalQuantity, "
                + "COALESCE(SUM(ct.Quantity * d.Price), 0) AS TotalCost "
                + "FROM DRINK_ORDER o "
                + "JOIN CLIENTE c ON o.ClientID = c.ClientID "
                + "LEFT JOIN CONTAINS ct ON o.OrderID = ct.OrderID "
                + "LEFT JOIN DRINK d ON ct.DrinkID = d.DrinkID "
                + "GROUP BY o.OrderID, o.Date, o.ClientID, c.Name "
                + "ORDER BY o.Date, o.OrderID";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Order List ---");
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("Order ID: " + rs.getString("OrderID"));
                System.out.println("Date: " + rs.getDate("Date"));
                System.out.println("Client: " + rs.getString("ClientName")
                        + " (" + rs.getString("ClientID") + ")");
                System.out.println("Total Quantity: " + rs.getInt("TotalQuantity"));
                System.out.println("Total Cost: $" + rs.getBigDecimal("TotalCost"));
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No orders found.");
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}
