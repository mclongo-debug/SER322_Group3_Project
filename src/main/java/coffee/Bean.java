package coffee;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Bean {
    private final Connection connection;
    private final Scanner scanner;

    public Bean(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    public void addBean() {
        System.out.print("Enter Bean ID: ");
        String beanID = scanner.nextLine();

        System.out.print("Enter Bean Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Roast Type: ");
        String roastType = scanner.nextLine();

        System.out.print("Enter Roast Date (YYYY-MM-DD): ");
        String roastDateText = scanner.nextLine();

        System.out.print("Enter Origin: ");
        String origin = scanner.nextLine();

        System.out.print("Enter Flavor Notes: ");
        String flavorNotes = scanner.nextLine();

        String sql = "INSERT INTO BEAN "
                + "(BeanID, Name, RoastType, RoastDate, Origin, FlavorNotes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, beanID);
            pstmt.setString(2, name);
            pstmt.setString(3, roastType);
            pstmt.setDate(4, Date.valueOf(roastDateText));
            pstmt.setString(5, origin);
            pstmt.setString(6, flavorNotes);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0
                    ? "Bean added successfully."
                    : "Bean was not added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void updateBean() {
        System.out.print("Enter Bean ID to update: ");
        String beanID = scanner.nextLine();

        System.out.print("Enter new Bean Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new Roast Type: ");
        String roastType = scanner.nextLine();

        System.out.print("Enter new Roast Date (YYYY-MM-DD): ");
        String roastDateText = scanner.nextLine();

        System.out.print("Enter new Origin: ");
        String origin = scanner.nextLine();

        System.out.print("Enter new Flavor Notes: ");
        String flavorNotes = scanner.nextLine();

        String sql = "UPDATE BEAN "
                + "SET Name = ?, RoastType = ?, RoastDate = ?, Origin = ?, FlavorNotes = ? "
                + "WHERE BeanID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, roastType);
            pstmt.setDate(3, Date.valueOf(roastDateText));
            pstmt.setString(4, origin);
            pstmt.setString(5, flavorNotes);
            pstmt.setString(6, beanID);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0
                    ? "Bean updated successfully."
                    : "Bean not found.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date. Use YYYY-MM-DD.");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void deleteBean() {
        System.out.print("Enter Bean ID to delete: ");
        String beanID = scanner.nextLine();

        String deleteUsedIn = "DELETE FROM USED_IN WHERE BeanID = ?";
        String deleteBean = "DELETE FROM BEAN WHERE BeanID = ?";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement usedInStmt = connection.prepareStatement(deleteUsedIn);
                 PreparedStatement beanStmt = connection.prepareStatement(deleteBean)) {

                usedInStmt.setString(1, beanID);
                usedInStmt.executeUpdate();

                beanStmt.setString(1, beanID);
                int rows = beanStmt.executeUpdate();

                if (rows > 0) {
                    connection.commit();
                    System.out.println("Bean deleted successfully.");
                } else {
                    connection.rollback();
                    System.out.println("Bean not found.");
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

    public void searchBean() {
        System.out.print("Enter Bean ID to search: ");
        String beanID = scanner.nextLine();

        String sql = "SELECT * FROM BEAN WHERE BeanID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, beanID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    printBean(rs);
                } else {
                    System.out.println("Bean not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void searchBeansByOrigin() {
        System.out.print("Enter bean origin to search: ");
        String origin = scanner.nextLine();

        String sql = "SELECT * FROM BEAN WHERE LOWER(Origin) = LOWER(?) ORDER BY Name";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, origin);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    printBean(rs);
                    System.out.println("-------------------------");
                }

                if (!found) {
                    System.out.println("No beans found for that origin.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void listBeans() {
        String sql = "SELECT * FROM BEAN ORDER BY Name";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- Bean List ---");
            boolean found = false;

            while (rs.next()) {
                found = true;
                printBean(rs);
                System.out.println("-------------------------");
            }

            if (!found) {
                System.out.println("No beans found.");
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void listDrinksUsingBean() {
        System.out.print("Enter Bean ID: ");
        String beanID = scanner.nextLine();

        String sql = "SELECT d.DrinkID, d.Name, d.Type, d.Flavor, d.Price "
                + "FROM DRINK d "
                + "JOIN USED_IN u ON d.DrinkID = u.DrinkID "
                + "WHERE u.BeanID = ? "
                + "ORDER BY d.Name";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, beanID);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n--- Drinks Using Bean " + beanID + " ---");
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println("Drink ID: " + rs.getString("DrinkID"));
                    System.out.println("Name: " + rs.getString("Name"));
                    System.out.println("Type: " + rs.getString("Type"));
                    System.out.println("Flavor: " + rs.getString("Flavor"));
                    System.out.println("Price: $" + rs.getBigDecimal("Price"));
                    System.out.println("-------------------------");
                }

                if (!found) {
                    System.out.println("No drinks found for that bean.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private void printBean(ResultSet rs) throws SQLException {
        System.out.println("Bean ID: " + rs.getString("BeanID"));
        System.out.println("Name: " + rs.getString("Name"));
        System.out.println("Roast Type: " + rs.getString("RoastType"));
        System.out.println("Roast Date: " + rs.getDate("RoastDate"));
        System.out.println("Origin: " + rs.getString("Origin"));
        System.out.println("Flavor Notes: " + rs.getString("FlavorNotes"));
    }
}
