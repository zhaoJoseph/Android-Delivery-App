package AppClasses;

import java.util.ArrayList;

public class Owner extends User{
    private Shop shop;
    private ArrayList<ItemDescription> menu;
    public Owner(String email, String username, ArrayList<Order> orders){
        super(email,username, orders);
        shop = new Shop(username+"'s Shop");
    }

    private String getStoreName(){
        return username + "'s Shop";

    }
}
