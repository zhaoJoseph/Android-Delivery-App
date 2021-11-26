package AppClasses;

public class ItemDescription {
    private String ItemName;
    private String Description;
    private String ItemID;
    public ItemDescription(){}
    public ItemDescription(String name, String desc,String id){
        ItemName = name;
        Description = desc;
        ItemID = id;
    }

    public String getName() {
        return ItemName;
    }
}
