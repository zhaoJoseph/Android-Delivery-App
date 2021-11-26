package AppClasses;

import java.util.ArrayList;

public class Shop {
    private String name;
    private ArrayList<ItemDescription> menu;
    public Shop(String n){
        menu = new ArrayList<ItemDescription>();
        name = n;
    }

    public String getName(){
        return name;
    }

    public ArrayList<ItemDescription> getMenu(){
        return menu;
    }
}
