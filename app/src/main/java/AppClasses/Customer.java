package AppClasses;

import java.util.ArrayList;

public class Customer extends User{
    public Customer(String em, String usrn, ArrayList<Order> ord){
        super(em,usrn);
    }
}
