package AppClasses;

public class ItemDescription {
    private String ItemName;
    private String Description;
    private String ItemID;
    private String brand;
    private double price;
    public ItemDescription(){}
    public ItemDescription(String name, String desc,String id, String brand, double price){
        ItemName = name;
        Description = desc;
        ItemID = id;
        this.brand = brand;
        this.price = price;
    }

    public String getName() {
        return ItemName;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice(){
        return price;
    }
}
