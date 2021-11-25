package AppClasses;

import java.util.ArrayList;

public class Shop {
    private String name;
    private ArrayList<ItemDescription> Menu;
    public Shop(String n){
        Menu = new ArrayList<ItemDescription>();
        name = n;
    }

}
