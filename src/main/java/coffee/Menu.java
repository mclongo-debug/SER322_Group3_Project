package coffee;

import java.sql.Connection;
import java.util.Scanner;

public class Menu {

    private Connection connection;
    private Scanner scanner;

    public Menu(Connection connection) {
        this.connection = connection;
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        System.out.println("Coffee Database Application");
        System.out.println("-----------------------------------------");
        System.out.println("Note: 'Cliente' is used throughout the application");
        System.out.println("because CLIENT is a reserved SQL keyword,");
        System.out.println("so the database table was named CLIENTE.");
        System.out.println();

        while (running) {
            printMenu();

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleClienteMenu();
                    break;
                case "2":
                    drinkMenu();
                    break;
                case "3":
                    handleBeanMenu();
                    break;
                case "4":
                    handleOrderMenu();
                    break;
                case "5":
                    System.out.println("Exiting application.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("==== Coffee Database Menu ====");
        System.out.println("1. Cliente");
        System.out.println("2. Drink");
        System.out.println("3. Bean");
        System.out.println("4. Order");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    private void handleClienteMenu() {
        System.out.println("Cliente menu will be added later.");
    }

    private void handleDrinkMenu() {
        System.out.println("Drink menu will be added later.");
    }

    private void handleBeanMenu() {
        System.out.println("Bean menu will be added later.");
    }

    private void handleOrderMenu() {
        System.out.println("Order menu will be added later.");
    }

   private void drinkMenu() {
         Drink drink = new Drink(connection);

         boolean running = true;

         while (running) {
             System.out.println();
             System.out.println("==== Drink Menu ====");
             System.out.println("1. Add Drink");
             System.out.println("2. Update Drink");
             System.out.println("3. Delete Drink");
             System.out.println("4. Search Drink");
             System.out.println("5. List Drinks");
             System.out.println("6. Back");

             System.out.print("Enter choice: ");
             String choice = scanner.nextLine();

             switch (choice) {
                 case "1":
                     drink.addDrink();
                     break;

                 case "2":
                     drink.updateDrink();
                     break;

                 case "3":
                     drink.deleteDrink();
                     break;

                 case "4":
                     drink.searchDrink();
                     break;

                 case "5":
                     drink.listDrinks();
                     break;

                 case "6":
                     running = false;
                     break;

                 default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
