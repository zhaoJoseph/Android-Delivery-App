package AppClasses;

import java.util.ArrayList;

public class Customer extends User{
    public Customer(String email, String username, ArrayList<Order> orders){
        super(email,username, orders);
    }
}
