package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;

import AppClasses.*;


public class CustomerStoreListActivity extends AppCompatActivity {

    private Button[] storeList;
    private ArrayList<Shop> allShop;
    private ArrayList<Shop> listOfShop;
    protected static Shop selected;

    private View.OnClickListener btnClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addStoresToPage();
        setContentView(R.layout.activity_customer_store_list);
        //TODO add a local reference to all the shops to allShop

    }

    private void addStoresToPage(){

        listOfShop = new ArrayList<>();

        EditText storeSearch = findViewById(R.id.store_search);
        String searchString = storeSearch.getText().toString().toLowerCase(Locale.ROOT);

        for (Shop shop: allShop){
            if (shop.getName().toLowerCase(Locale.ROOT).contains(searchString))
                listOfShop.add(shop);
        }

        //https://stackoverflow.com/questions/19088317/list-of-button-in-android
        btnClicked = v -> {
            Object tag = v.getTag();
            int tags = Integer.parseInt(tag.toString());
            selected = listOfShop.get(tags);
        };

        //get the linear layout so we can add everything into it
        LinearLayout frame = findViewById(R.id.linear_layout);

        //remove all store buttons on the page
        for (int i = 0; i < storeList.length; i++) {
            frame.removeView(storeList[i]);
        }

        //add everything to the store page
        storeList = new Button[listOfShop.size()];

        for (int i = 0; i < storeList.length; i++) {
            storeList[i] = new Button(this);
            //code from https://stackoverflow.com/a/20512925
            storeList[i].setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, 50));
            storeList[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            storeList[i].setText(listOfShop.get(i+1).getName());
            storeList[i].setTag(i+1);//i+1 is the referencing number in the arraylist
            storeList[i].setOnClickListener(btnClicked);
            frame.addView(storeList[i]);

        }




    }



}