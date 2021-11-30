package com.example.b07_project.Model;

public class ItemData {
    private ItemDescriptionData data;
    private int quantity;
    public ItemData(ItemDescriptionData i,int quantity){
        data=i;
        this.quantity = quantity;
    }
    public ItemData(){}
    public int getQuantity(){
        return quantity;
    }
    public ItemDescriptionData getData(){return data;}
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
