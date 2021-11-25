package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CustomerStoreListActivity extends AppCompatActivity {

    Button[] storeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_store_list);
    }

    private void addStores(){

        //get the linear layout so we can add everything into it
        LinearLayout frame = (LinearLayout) findViewById(R.id.linear_layout);

        //TODO add in access to arraylist of stores and have storelist be referenced by it


        storeList = new Button[];


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
}