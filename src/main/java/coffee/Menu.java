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
                    clienteMenu();
                    break;
                case "2":
                    drinkMenu();
                    break;
                case "3":
                    beanMenu();
                    break;
                case "4":
                    orderMenu();
                    break;
                case "5":
                    RatingMenu();
                    break;
                case "6":
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
        System.out.println("5. Rating");
        System.out.println("6. Exit");
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

    private void clienteMenu() {

        Cliente cliente = new Cliente(connection);

        boolean running = true;

        while (running) {

            System.out.println("\n==== Cliente Menu ====");
            System.out.println("1. Add Cliente");
            System.out.println("2. Update Cliente");
            System.out.println("3. Delete Cliente");
            System.out.println("4. Search Cliente");
            System.out.println("5. List Clientes");
            System.out.println("6. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cliente.addCliente();
                    break;
                case "2":
                    cliente.updateCliente();
                    break;
                case "3":
                    cliente.deleteCliente();
                    break;
                case "4":
                    cliente.searchCliente();
                    break;
                case "5":
                    cliente.listClientes();
                    break;
                case "6":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
   
    private void beanMenu() {

       Bean bean = new Bean(connection);

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==== Bean Menu ====");
            System.out.println("1. Add Bean");
            System.out.println("2. Update Bean");
            System.out.println("3. Delete Bean");
            System.out.println("4. Search Bean");
            System.out.println("5. List Beans");
            System.out.println("6. Search Beans by Origin");
            System.out.println("7. List Drinks Using Bean");
            System.out.println("8. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    bean.addBean();
                    break;

                case "2":
                    bean.updateBean();
                    break;

                case "3":
                    bean.deleteBean();
                    break;

                case "4":
                    bean.searchBean();
                    break;

                case "5":
                    bean.listBeans();
                    break;

                case "6":
                    bean.searchBeansByOrigin();
                    break;

                case "7":
                    bean.listDrinksUsingBean();
                    break;

                case "8":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }    
    }

    private void orderMenu() {

        Order order = new Order(connection);

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==== Order Menu ====");
            System.out.println("1. Add Order");
            System.out.println("2. Add Drink To Order");
            System.out.println("3. Update Order");
            System.out.println("4. Update Drink Quantity");
            System.out.println("5. Delete Order");
            System.out.println("6. Remove Drink From Order");
            System.out.println("7. Search Order");
            System.out.println("8. List Orders");
            System.out.println("9. Back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    order.addOrder();
                    break;

                case "2":
                    order.addDrinkToOrder();
                    break;

                case "3":
                    order.updateOrder();
                    break;

                case "4":
                    order.updateDrinkQuantity();
                    break;

                case "5":
                    order.deleteOrder();
                    break;

                case "6":
                    order.removeDrinkFromOrder();
                    break;

                case "7":
                    order.searchOrder();
                    break;

                case "8":
                    order.listOrders();
                    break;

                case "9":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    private void RatingMenu() {
        Rating rating = new Rating(connection);

        System.out.println("\n==== Rating Menu ====");
        System.out.println("1. Add Rating");
        System.out.println("2. Delete Rating");
        System.out.println("3. Search Rating");
        System.out.println("4. List All Ratings");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                rating.addRating();
                break;

            case "2":
                rating.deleteRating();
                break;

            case "3":
                rating.searchRating();
                break;

            case "4":
                rating.listRatings();
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }
}
