package AppClasses;

import java.util.ArrayList;

public class Owner extends User{
    private Shop shop;
    public Owner(String em, String usrn){
        super(em,usrn);
        shop = new Shop(username+"'s Shop");
    }
}
