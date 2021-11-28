package AppClasses;

import java.util.ArrayList;

public class Shop {
    private String name;
    private ArrayList<ItemDescription> menu;
    public Shop(String name){
        this.menu = new ArrayList<ItemDescription>();
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<ItemDescription> getMenu(){
        return menu;
    }

    public void addToMenu(ItemDescription item){
        menu.add(item);
    }
}
