package AppClasses;

import java.util.ArrayList;

public class Order {
    private ArrayList<Item> content;
    private final String orderOwner;
    private final String orderCustomer;
    private final int orderID;

    public Order(String customer,String owner, int orderID){
        this.orderCustomer = customer;
        this.orderOwner = owner;
        this.content = new ArrayList<Item>();
        this.orderID = orderID;
    }

    public int getID(){
        return orderID;
    }
    public String getOrderOwner(){
        return orderOwner;
    }

    public String getOrderCustomer(){
        return orderCustomer;
    }

    public ArrayList<Item> getContent(){
        return content;
    }


}
