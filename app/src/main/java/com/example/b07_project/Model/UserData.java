package com.example.b07_project.Model;

public class UserData {
    private String email;
    private Boolean isCustomer;
    public UserData(String em, Boolean Type){
        email=em;
        isCustomer=Type;
    }
    public UserData(){}
    public Boolean getIsCustomer(){return isCustomer;}
    public String getEmail(){return email;}
}
