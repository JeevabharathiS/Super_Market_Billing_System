package SMPack;
import java.util.*;

public class Customer extends User{
    private Market market;
    private double credit;
    private int loyaltyScore;
    private List<Bill> purchaseHistory;
    private Map<String, CartItem> cart; //ID -> cartItem(obj)
    private Admin byAdmin;
    private double totalSpent;

    public Customer(String email, String password, Admin byAdmin ,Market market){
        super(email, password, "Customer");
        this.market = market;
        this.credit = 1000.0;
        this.loyaltyScore = 0;
        this.purchaseHistory = new ArrayList<>();
        this.cart = new HashMap<>();
        this.byAdmin = byAdmin;
        this.totalSpent = 0.0;
    }

    Scanner sc = new Scanner(System.in);

    @Override
    public void showMenu(){
        while(true){
            System.out.println();
            System.out.println("===== Customer Menu =====");
            System.out.println("\n1. View Available Items");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Modify Cart");
            System.out.println("5. Clear Cart");
            System.out.println("6. Proceed to Payment");
            System.out.println("7. View My Purchase History");
            System.out.println("0. Log Out"); 
            
            System.out.println();
            int choice = Utils.getValidInteger(sc, "Enter Your Choice : ");
            switch (choice) {
                case 1:
                    System.out.println();
                    viewAvailableItems();
                    break;

                case 2:
                    System.out.println();
                    String p_id = Utils.getValidString(sc, "Enter the Product ID : ");
                    addItemToCart(p_id);
                    break;

                case 3:
                    System.out.println();
                    viewCart();
                    break;

                case 4:
                    System.out.println();
                    modifyCart();
                    break;
                
                case 5: 
                    System.out.println();
                    clearCart();
                    break;

                case 6:
                    System.out.println();
                    proceedToPayment();
                    break;

                case 7:
                    System.out.println();
                    viewPurchaseHistory();
                    break;

                case 8:
                    System.out.println();
                    System.out.println("Logging Out..\n");
                    return;

                case 0:
                    System.out.println();
                    System.out.println("Logging Out...");
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid Choice\n");
                    break;
            }
        }
    }



    public double getCredit(){
        return credit;
    }

    public int getLoyaltyScore(){
        return loyaltyScore;
    }

    public double getCartTotal() {
        double total = 0;
        for (CartItem ci : cart.values()) {
            total += ci.getSubtotal();
        }
        return total;
    }

    public void viewAvailableItems(){
        List<Item> items = market.getAllItems();
        System.out.println("------ Available Items ------");
        System.out.println(" ID   -   NAME   -   QUANTITY   -   COST   -   TIMES BOUGHT");
        for(Item item: items){
            if(item.isAvailable()){
                System.out.println(item.getAsRow());
            }
        }
    }

    public void addItemToCart(String id){
        Item item = market.getItemById(id);
        if(item == null){
            System.out.println("No Such Product Exists.");
            return;
        }
        if(!item.isAvailable()){
            System.out.println("That Specific Product is not Available");
            return;
        }

        int quantity = Utils.getValidInteger(sc, "Enter The Quantity : ");
        if(quantity > item.getQuantity()){
            System.out.println("Not Enough Stock Available (Available Stock : "+item.getQuantity()+")");
            return;
        }

        if(cart.containsKey(id)){
            CartItem ci = cart.get(id);
            ci.setQuantity(ci.getQuantity()+quantity);
        }else{
            cart.put(id, new CartItem(item, quantity));
        }

        System.out.println("Item Added to Cart Successfully");
    }

    public void viewCart(){
        if(cart.isEmpty()){
            System.out.println("Your Cart is Empty");
            return;
        }

        double total = 0.0;
        System.out.println("------ Your Cart ------");
        for(CartItem ci: cart.values()){
            System.out.println(ci.getAsRow());
            total += ci.getSubtotal();
        }
        System.out.println();
        System.out.println("Total Cost : "+total);
        System.out.println();
        return;
    }

    public void modifyCart() {
    if (cart.isEmpty()) {
        System.out.println("Your cart is empty.");
        return;
    }

    viewCart();
    String pid = Utils.getValidString(sc, "Enter Product ID to modify: ");
    if (!cart.containsKey(pid)) {
        System.out.println("Product not in cart.");
        return;
    }

    int newQty = Utils.getValidInteger(sc, "Enter new quantity (0 to remove): ");
    if (newQty == 0) {
        cart.remove(pid);
        System.out.println("Item removed from cart.");
    } else {
        Item item = market.getItemById(pid);
        if (newQty > item.getQuantity()) {
            System.out.println("Not enough stock.");
        } else {
            cart.get(pid).setQuantity(newQty);
            System.out.println("Quantity updated.");
        }
    }
}

    public void clearCart(){
        if(cart.isEmpty()){
            System.out.println("Cart is Cleared");
            return;
        }
        cart.clear();
        System.out.println("Cart is Cleared");
    }

    public void proceedToPayment(){
        
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty. Add items before proceeding.");
            return;
        }

        double total = getCartTotal();
        double discount = 0.0;
        int temp_loyaltyScore = loyaltyScore;

        if(loyaltyScore >= 50){
            discount += 100;
            loyaltyScore -= 50;
        }

        boolean flatReward = false;
        if(total >= 5000){
            flatReward = true;
            discount += 100;
        }

        double finalAmount = total - discount;

        if(credit < finalAmount){
            System.out.println("Insufficient Credit. Payment Failed");
            loyaltyScore = temp_loyaltyScore;
            return;
        }
        credit -= finalAmount;

        if(!flatReward){
            int earn = (int)(total/100);
            loyaltyScore += earn;
        }

        for(CartItem ci: cart.values()){
            Item item = ci.getItem();
            int qty = ci.getQuantity();
            item.getItem(qty);
        }

        int billNo = market.getNextBillNo();
        Bill bill = new Bill(billNo, new Date(), this.getEmail(), new ArrayList<>(cart.values()), total, credit, loyaltyScore, discount);
        purchaseHistory.add(bill);
        bill.printBill();
        clearCart();
        byAdmin.incrementSalesMade();
        totalSpent += finalAmount;
    }

    public void viewPurchaseHistory(){
        if(purchaseHistory.isEmpty()){
            System.out.println("\nYou haven't made any purchases yet.");
            return;
        }
        System.out.println("\n------ Purchase History ------\n");
        for(Bill bill: purchaseHistory){
            System.out.println(bill.getSummaryRow());
        }
    }

    public void addCredit(double amt){
        credit += amt;
    }

    public double getTotalSpent(){
        return this.totalSpent;
    }


}
