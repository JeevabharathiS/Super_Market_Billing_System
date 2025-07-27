package SMPack;

public class CartItem {
    private Item item;
    private int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int q) {
        this.quantity = q;
    }

    public double getSubtotal() {
        return item.getCost() * quantity;
    }

    public String getAsRow() {
        return String.format("%s | Qty: %d | Subtotal: ₹%.2f", item.getName(), quantity, getSubtotal());
    }
}
