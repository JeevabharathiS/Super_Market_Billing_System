import java.util.*;
import SMPack.*;

class Demo{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Market market = new Market();

        market.addUser(new Admin("admin1@gmail.com", "admin1pass", market));
        market.addUser(new Admin("admin2@gmail.com", "admin2pass", market));

    }
}

