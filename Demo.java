import java.util.*;
import SMPack.*;

class Demo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Market market = new Market();

        market.addUser(new Admin("admin1@gmail.com", "admin1pass", market));
        market.addUser(new Admin("admin2@gmail.com", "admin2pass", market));

        while (true) {
            System.out.println("\n=== Welcome to Super Market Billing System ===");
            System.out.println();
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.println();

            int choice = Utils.getValidInteger(sc, "Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println();
                    String email = Utils.getValidString(sc, "Enter Email: ");
                    String password = Utils.getValidString(sc, "Enter Password: ");

                    User user = market.authenticateUser(email, password);
                    if (user != null) {
                        System.out.println("\nLogin successful as " + user.getRole() + "!");
                        user.showMenu();
                    } else {
                        System.out.println("Invalid email or password. Try again.");
                    }
                    break;

                case 0:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
