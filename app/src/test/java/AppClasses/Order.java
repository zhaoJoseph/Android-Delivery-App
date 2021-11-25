package AppClasses;

import java.util.ArrayList;

public class Order {
    private ArrayList<Item> content;
    private String OrderMakerUsername;
    private String OrderHeader;
    public Order(){}
    public Order(String header,String whoismaking){
        OrderHeader = header;
        OrderMakerUsername = whoismaking;
        content = new ArrayList<Item>();
    }
}
