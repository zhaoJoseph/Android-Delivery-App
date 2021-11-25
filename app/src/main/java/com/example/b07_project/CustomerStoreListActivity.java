package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class CustomerStoreListActivity extends AppCompatActivity {

    Button[] storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addStores();
        setContentView(R.layout.activity_customer_store_list);

    }

    private void addStores(){

        //get the linear layout so we can add everything into it
        LinearLayout frame = findViewById(R.id.linear_layout);

        //TODO add in access to arraylist of stores and have store list be referenced by it


        storeList = new Button[27];


        //https://stackoverflow.com/questions/19088317/list-of-button-in-android
        for (int i = 0; i < storeList.length; i++) {
            storeList[i] = new Button(this);
            storeList[i].setHeight(50);
            storeList[i].setWidth(50);
            storeList[i].setTag(i);
            //TODO add in the click detection and the methods which would be required for it
            frame.addView(storeList[i]);
        }





    }

}