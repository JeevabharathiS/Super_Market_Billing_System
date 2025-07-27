package SMPack;
import java.util.*;

public class Item {
    private String id;
    private String name;
    private int quantity;
    private int timesBought;
    private double cost;

    public Item(String id, String name, int quantity, double cost){
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
        this.timesBought = 0;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setQuantity(int newQuantity){
        this.quantity = newQuantity;
    }

    public void setCost(double newCost){
        this.cost = newCost;
    }

    public boolean isAvailable(){
        return quantity>0;
    }

    public boolean getItem(int q){
        if(isAvailable()){
            this.quantity -= q;
            this.timesBought += q;
            return true; 
        }
        return false;
    }

    public String getAsRow(){
        return (id +" - "+ name +" - "+ quantity +" - "+ cost +" - "+ timesBought);
    }

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.id;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public double getCost(){
        return this.cost;
    }

    public int getTimesBought(){
        return timesBought;
    }
}
