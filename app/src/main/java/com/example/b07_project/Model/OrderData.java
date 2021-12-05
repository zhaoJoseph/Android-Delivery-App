package com.example.b07_project.Model;


import com.google.firebase.database.Exclude;

import java.util.List;
import java.util.Map;

public class OrderData {
    private String orderBy;
    private String orderID;
    private String orderingFrom;
    private List<ItemData> items;
    private boolean isComplete;
    public OrderData(String by,String from,List<ItemData>items){
        //we set orderid after polling firebase
        this.orderBy = by;
        this.orderingFrom = from;
        this.items = items;
        this.isComplete = false;
    }
    public OrderData(){}
    @Exclude
    public void SetOrderID(String s){orderID = s;}
    @Exclude
    public void SetIsComplete(Boolean b){isComplete = b;}

    public String GetOrderID() { return orderID;}
    public String GetOrderBy(){return orderBy;}
    public String GetOrderingFrom(){ return orderingFrom;}
    public List<ItemData> GetItems(){return items;}
    public Boolean GetIsComplete(){return isComplete;}
}
