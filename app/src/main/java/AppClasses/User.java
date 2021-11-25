package AppClasses;

import java.util.ArrayList;

abstract public class User {

    protected String username;

    protected String email;
    protected ArrayList<Order> orders;
    public ArrayList<Order> GetOrders(){
        return orders;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public User(String e,String u){
        this.email = e;
        this.username = u;
        this.orders = new ArrayList<Order>();
    }
}
