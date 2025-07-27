package SMPack;
import java.util.*;

public class Market {
    Map<String, Item> items; //ID -> Item(obj)
    Map<String, User> users; //Email -> User(ab obj)
    private int nextBillNo = 1001;

    public void Market(){
        items = new HashMap<>();
        users = new HashMap<>();
    }

    public void addUser(User user){
        users.put(user.getEmail(), user);
    }

    public User getUser(String email){
        return users.get(email);
    }

    public Customer getCustomer(String email){
        User user = getUser(email);
        if(user instanceof Customer){
            return (Customer) user;
        }
        return null;
    }

    public boolean emailExists(String email){
        return users.containsKey(email);
    }

    public void addItem(Item item){
        items.put(item.getID(), item);
    }

    public void deleteBook(String id){
        Item item = getItemById(id);
        if(item != null){
            items.remove(id);
            System.out.println("\nItem Removed Successfully.");
        }else{
            System.out.println("\nCouldn't Find that Item.");
        }
    }

    public List<Item> getAllItems(){
        return new ArrayList<>(items.values());
    }

    public Item getItemById(String id){
        return items.get(id);
    }

    public List<User> getAllUsers(){
        return (new ArrayList<>(users.values()));
    }

    public int getNextBillNo(){
        return nextBillNo++;
    }
}
