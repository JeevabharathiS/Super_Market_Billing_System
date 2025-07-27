package SMPack;
import java.util.*;

public class Admin extends User {
    private Market market;
    private int salesMade;
    
    public Admin(String email, String password, Market market){
        super(email, password, "Admin");
        this.market = market;
        this.salesMade = 0;
    }

    Scanner sc = new Scanner(System.in);

    @Override
    public void showMenu(){
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
                    
                    break;
            
                default:
                    break;
            }
        }
    }


    public int getSalesMade(){
        return salesMade;
    }
}
