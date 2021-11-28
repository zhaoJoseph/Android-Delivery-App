package AppClasses;

public class Item extends ItemDescription{
    private ItemDescription item_description;
    private int quantity;
    public Item(){}
    public Item(ItemDescription item_description,Integer quantity){
        this.quantity = quantity;
        this.item_description = item_description;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public ItemDescription getItemDescription(){
        return item_description;
    }
}
