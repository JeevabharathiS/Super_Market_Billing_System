package SMPack;

import java.util.*;
import java.text.SimpleDateFormat;

public class Bill {
    private int billNo;
    private Date billDate;
    private String customerEmail;
    private List<CartItem> items;
    private double totalAmount;
    private double walletAfter;
    private int loyaltyAfter;
    private double discount;

    public Bill(int billNo, Date billDate, String customerEmail,
                List<CartItem> items, double totalAmount, double walletAfter, int loyaltyAfter, double discount) {
        this.billNo = billNo;
        this.billDate = billDate;
        this.customerEmail = customerEmail;
        this.items = items;
        this.totalAmount = totalAmount;
        this.walletAfter = walletAfter;
        this.loyaltyAfter = loyaltyAfter;
        this.discount = discount;
    }

    public int getBillNo() {
        return billNo;
    }

    public String getSummaryRow() {
        return String.format("Bill #%d | Date: %s | Total: ₹%.2f",
            billNo, formatDate(billDate), totalAmount);
    }

    public void printBill() {
        System.out.println("========================================");
        System.out.println("             SUPER MARKET BILL          ");
        System.out.println("========================================");
        System.out.println("Bill No       : " + billNo);
        System.out.println("Date          : " + formatDate(billDate));
        System.out.println("Customer      : " + customerEmail);
        System.out.println("----------------------------------------");
        System.out.printf("%-15s %4s %7s %9s\n", "Item", "Qty", "Price", "Subtotal");
        System.out.println("----------------------------------------");

        for (CartItem ci : items) {
            Item item = ci.getItem();
            System.out.printf("%-15s %4d %7.2f %9.2f\n",
                item.getName(), ci.getQuantity(), item.getCost(), ci.getSubtotal());
        }

        System.out.println("----------------------------------------");
        System.out.printf("Original Amount           : ₹%.2f\n", totalAmount);
        System.out.printf("Discount Applied          : ₹%.2f\n", discount);
        System.out.printf("Total Amount Paid         : ₹%.2f\n", (totalAmount - discount));
        System.out.printf("Wallet Balance After Bill : ₹%.2f\n", walletAfter);
        System.out.printf("Loyalty Score After Bill  : %d points\n", loyaltyAfter);
        System.out.println("========================================");
        System.out.println("         Thank you! Visit Again!        ");
        System.out.println("========================================");
    }


    private String formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
}
