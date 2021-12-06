package com.example.b07_project.Model;

import com.google.firebase.database.Exclude;

public class ItemData {
    private ItemDescriptionData data;
    private int quantity;
    public ItemData(ItemDescriptionData i,int quantity){
        data=i;
        this.quantity = quantity;
    }
    public ItemData(){}
    @Exclude
    @Override
    public int hashCode(){
        return data.hashCode();
    }
    @Exclude
    @Override
    public boolean equals(Object O){
        if(O == null)
            return false;
        if(O == this)
            return true;
        ItemData i = (ItemData) O;
        if((i.getData().equals(this.data)))
            return true;
        return false;
    }
    public int getQuantity(){
        return quantity;
    }
    public ItemDescriptionData getData(){return data;}
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}