package SMPack;
import java.util.*;

public class ReportGenerator {

    public static void lowStockItems(Market market, int threshold){
        System.out.println("----- Items With Stock Below " + threshold + " -----\n");
        for(Item item : market.getAllItems()){
            if(item.getQuantity() < threshold){
                System.out.println(item.getAsRow());
            }
        }
    }

    public static void neverBoughtItems(Market market){
        System.out.println("----- Items Never Bought -----\n");
        for(Item item : market.getAllItems()){
            if(item.getTimesBought() == 0){
                System.out.println(item.getAsRow());
            }
        }
    }

    public static void topCustomers(Market market){
        System.out.println("----- Top Customers by Amount Spent -----\n");
        List<Customer> customers = new ArrayList<>();
        for(User user : market.getAllUsers()){
            if(user instanceof Customer){
                customers.add((Customer) user);
            }
        }
        customers.sort((a, b) -> Double.compare(b.getTotalSpent(), a.getTotalSpent()));
        for(Customer c : customers){
            System.out.println(c.getEmail() + " - ₹" + c.getTotalSpent());
        }
    }

    public static void topAdmins(Market market){
        System.out.println("----- Admins by Sales Made -----\n");
        List<Admin> admins = new ArrayList<>();
        for(User user : market.getAllUsers()){
            if(user instanceof Admin){
                admins.add((Admin) user);
            }
        }
        admins.sort((a, b) -> Integer.compare(b.getSalesMade(), a.getSalesMade()));
        for(Admin a : admins){
            System.out.println(a.getEmail() + " - Sales: " + a.getSalesMade());
        }
    }
}
