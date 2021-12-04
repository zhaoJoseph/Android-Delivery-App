package com.example.b07_project.Model;

public class ItemDescriptionData {
    private String name;
    private String brand;
    private Double price;
    public ItemDescriptionData(String name,String Brand,double price){
        this.name = name;
        this.brand = Brand;
        this.price = price;
    }

    @Override
    public boolean equals(Object O){
        if(O == null)
            return false;
        if(O == this)
            return true;
        ItemDescriptionData i = (ItemDescriptionData) O;
        if((i.getName().equals(this.getName())))
            return true;
        return false;
    }

    public ItemDescriptionData(){}
    public String getName(){return name;}
    public String getBrand(){return brand;}
    public Double getPrice(){return price;}

}
