package AppClasses;

import java.util.ArrayList;

abstract public class User {

    protected String username;

    protected String email;
    protected ArrayList<Order> orders;

    public User(String email,String username, ArrayList<Order> orders){
        this.email = email;
        this.username = username;
        this.orders = orders;
    }

    public ArrayList<Order> GetOrders(){

        return orders;
    }

    public String getEmail() {

        return email;
    }

    public String getUsername() {

        return username;
    }

    public void addToOrders(Order order){
        orders.add(order);
    }

}
