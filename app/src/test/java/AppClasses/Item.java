package AppClasses;

public class Item {
    private ItemDescription ItemDesc;
    private Integer Quantity;
    public Item(){}
    public Item(ItemDescription id,Integer q){
        Quantity = q;
        ItemDesc = id;
    }
}
