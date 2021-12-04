package com.example.b07_project.Model;


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
    public void SetOrderID(String s){orderID = s;}
    public void setisComplete(Boolean b){isComplete = b;}

    public String getOrderID() { return orderID;}
    public String GetOrdererID(){return orderBy;}
    public String GetShopOwnerID(){ return orderingFrom;}
    public List<ItemData> getItems(){return items;}
    public Boolean getisComplete(Boolean b){return isComplete;}
}
