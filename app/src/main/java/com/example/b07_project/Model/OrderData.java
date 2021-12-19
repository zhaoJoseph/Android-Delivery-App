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
        this.orderID = "NOT SET";
        this.orderingFrom = from;
        this.items = items;
        this.isComplete = false;
    }
    public OrderData(){}

    @Exclude
    public void setOrderID(String s){orderID = s;}
    @Exclude
    public void setIsComplete(Boolean b){isComplete = b;}

    public String getOrderID() { return orderID;}
    public String getOrderBy(){return orderBy;}
    public String getOrderingFrom(){ return orderingFrom;}
    public List<ItemData> getItems(){return items;}
    public Boolean getIsComplete(){return isComplete;}
}
