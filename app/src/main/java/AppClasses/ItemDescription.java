package AppClasses;

public class ItemDescription {
    private String itemName;
    private String brand;
    private double price;
    private int itemID;
    public ItemDescription(){}
    public ItemDescription(String name, int id, String brand, double price){
        this.itemName = name;
        this.itemID = id;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return itemName;
    }

    public int getID(){
        return itemID;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice(){
        return price;
    }
}
