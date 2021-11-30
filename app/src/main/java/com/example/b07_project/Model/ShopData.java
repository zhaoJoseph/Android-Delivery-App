package com.example.b07_project.Model;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.b07_project.signUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@IgnoreExtraProperties
public class ShopData {
    //WARNING YOU NEED EMPTY CONSTRUCTORS, GETTERS AND SPECIFIC STYLING FOR FIREBASE
    //CONSULT BEFORE CHANGING
    private String ownerID;
    private String shop_name;
    private List<ItemDescriptionData> items;
    public ShopData(String id,String Shop_name,List<ItemDescriptionData> items){
        this.shop_name = Shop_name;
        this.ownerID = id;
        this.items = items;
    }
    public ShopData(){}
    public String getOwnerID(){return ownerID;}
    public String getShop_name(){return shop_name;}
    @Exclude
    public void setName(String name){
        shop_name=name;
    }
    @Exclude
    public void setItems(List<ItemDescriptionData> i){items=i;}
    public List<ItemDescriptionData> getItems(){return items;}

}
