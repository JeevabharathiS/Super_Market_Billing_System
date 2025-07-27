package SMPack;
import java.util.*;

public class Admin extends User {
    private Market market;

    public Admin(String email, String password, Market market){
        super(email, password, "Admin");
        this.market = market;
    }

    @Override
    public void showMenu(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println();
            System.out.println("===== Admin Menu =====");
            System.out.println("\n1. Add Item");
            System.out.println("2. Modify Item");
            System.out.println("3. Delete Item");
            System.out.println("4. Search Item");
            System.out.println("5. Add a Customer");
            System.out.println("6. Add an Admin");
            System.out.println("7. Increase Customer Credit");
            System.out.println("8. View All Items (Sorted by Name)");
            System.out.println("9. View All Items (Sorted by Price)");
            System.out.println("10. View Items with Low Stock");
            System.out.println("11. View Items that never been bought");
            System.out.println("12. View Customers who Purchased more");
            System.out.println("13. View Admins who Made more Sales");
            System.out.println("0. Log Out");

            System.out.println();
            int choice = Utils.getValidInteger(sc, "Enter Your Choice : ");

            switch (choice) {
                case 1:
                    System.out.println();
                    String id = Utils.getValidString(sc, "Enter Item ID: ");
                    String name = Utils.getValidString(sc, "Enter Item Name: ");
                    int qty = Utils.getValidInteger(sc, "Enter Quantity: ");
                    double cost = Utils.getValidDouble(sc, "Enter Cost: ");
                    addItem(new Item(id, name, qty, cost));
                    break;

                case 2:
                    System.out.println();
                    String mid = Utils.getValidString(sc, "Enter Item ID: ");
                    int newQty = Utils.getValidInteger(sc, "Enter New Quantity: ");
                    double newCost = Utils.getValidDouble(sc, "Enter New Cost: ");
                    modifyItem(mid, newQty, newCost);
                    break;

                case 3:
                    System.out.println();
                    String did = Utils.getValidString(sc, "Enter Item ID: ");
                    deleteItem(did);
                    break;

                case 4:
                    System.out.println();
                    String searchName = Utils.getValidString(sc, "Enter Name to Search: ");
                    searchItemByName(searchName);
                    break;

                case 5:
                    System.out.println();
                    String cemail = Utils.getValidString(sc, "Enter Customer Email: ");
                    String cpass = Utils.getValidString(sc, "Enter Customer Password: ");
                    addCustomer(new Customer(cemail, cpass, this, market));
                    break;

                case 6:
                    System.out.println();
                    String aemail = Utils.getValidString(sc, "Enter Admin Email: ");
                    String apass = Utils.getValidString(sc, "Enter Admin Password: ");
                    addAdmin(new Admin(aemail, apass, market));
                    break;

                case 7:
                    System.out.println();
                    String custEmail = Utils.getValidString(sc, "Enter Customer Email: ");
                    double amount = Utils.getValidDouble(sc, "Enter Amount to Add: ");
                    increaseCredit(custEmail, amount);
                    break;

                case 8:
                    System.out.println();
                    viewItemsSortedByName();
                    break;

                case 9:
                    System.out.println();
                    viewItemsSortedByPrice();
                    break;

                case 10:
                    System.out.println();
                    int threshold = Utils.getValidInteger(sc, "Enter Stock Threshold: ");
                    ReportGenerator.lowStockItems(market, threshold);
                    break;

                case 11:
                    System.out.println();
                    ReportGenerator.neverBoughtItems(market);
                    break;

                case 12:
                    System.out.println();
                    ReportGenerator.topCustomers(market);
                    break;

                case 13:
                    System.out.println();
                    ReportGenerator.topAdmins(market);
                    break;

                case 0:
                    System.out.println();
                    System.out.println("Logging Out...");
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid Choice");
                    break;
            }
        }
    }

    public void addItem(Item item){
        market.addItem(item);
        System.out.println("Item Added Successfully");
    }

    public void modifyItem(String id, int qty, double cost){
        Item item = market.getItemById(id);
        if(item != null){
            item.setQuantity(qty);
            item.setCost(cost);
            System.out.println("Item Updated");
        } else {
            System.out.println("Item Not Found");
        }
    }

    public void deleteItem(String id){
        market.deleteItem(id);
    }

    public void searchItemByName(String name){
        List<Item> items = market.getAllItems();
        for(Item item : items){
            if(item.getName().toLowerCase().contains(name.toLowerCase())){
                System.out.println(item.getAsRow());
            }
        }
    }

    public void addCustomer(Customer c){
        if(market.emailExists(c.getEmail())){
            System.out.println("Email Already Exists");
        } else {
            market.addUser(c);
            System.out.println("Customer Added");
        }
    }

    public void addAdmin(Admin a){
        if(market.emailExists(a.getEmail())){
            System.out.println("Email Already Exists");
        } else {
            market.addUser(a);
            System.out.println("Admin Added");
        }
    }

    public void increaseCredit(String email, double amt){
        Customer c = market.getCustomer(email);
        if(c != null){
            c.addCredit(amt);
            System.out.println("Credit Added");
        } else {
            System.out.println("Customer Not Found");
        }
    }

    public void viewItemsSortedByName(){
        List<Item> items = market.getAllItems();
        System.out.println("----- Items Sorted By Name -----\n");
        items.sort(Comparator.comparing(Item::getName));
        for(Item item : items){
            System.out.println(item.getAsRow());
        }
    }

    public void viewItemsSortedByPrice(){
        List<Item> items = market.getAllItems();
        System.out.println("----- Items Sorted By Price -----\n");
        items.sort(Comparator.comparing(Item::getCost));
        for(Item item : items){
            System.out.println(item.getAsRow());
        }
    }

    public int getSalesMade(){
        return salesMade;
    }

    private int salesMade = 0;

    public void incrementSalesMade(){
        salesMade++;
    }
}
