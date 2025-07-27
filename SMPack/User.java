package SMPack;
import java.util.*;

public abstract class User {
    protected String email;
    protected String password;
    protected String role;
    public abstract void showMenu();

    public User(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean authenticate(String email, String password){
        if(this.email.equals(email) && this.password.equals(password)){
            return true;
        }
        return false;
    }

    public String getEmail(){
        return this.email;
    }

    public String getRole(){
        return this.role;
    }

}
