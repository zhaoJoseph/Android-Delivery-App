package com.example.b07_project;

//John Li

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import AppClasses.Item;
import AppClasses.ItemDescription;
import AppClasses.Shop;

public class CustomerStoreItemListActivity extends AppCompatActivity {

    private ArrayList<ItemDescription> listOfAllItem;
    private ArrayList<ItemDescription> queriedItem;
    private View.OnClickListener btnClicked;
    protected static ItemDescription selectedItem;
    private Button[] itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_store_item_list);
        listOfAllItem = CustomerStoreListActivity.selected.getMenu();
        TextView storeName = (TextView) findViewById(R.id.store_name);
        storeName.setText(CustomerStoreListActivity.selected.getName());
        addItemToPage();

    }

    private void addItemToPage(){

        queriedItem = new ArrayList<>();

        EditText storeSearch = findViewById(R.id.store_search);
        String searchString = storeSearch.getText().toString().toLowerCase(Locale.ROOT);

        for (ItemDescription item: listOfAllItem){
            if (item.getName().toLowerCase(Locale.ROOT).contains(searchString))
                queriedItem.add(item);
        }

        //https://stackoverflow.com/questions/19088317/list-of-button-in-android
        btnClicked = v -> {
            Object tag = v.getTag();
            int tags = Integer.parseInt(tag.toString());
            selectedItem = queriedItem.get(tags);

        };

        //get the linear layout so we can add everything into it
        LinearLayout frame = findViewById(R.id.item_list);

        //remove all store buttons on the page
        frame.removeAllViews();


        //add everything to the store page
       itemList = new Button[queriedItem.size()];

        for (int i = 0; i < itemList.length; i++) {
            itemList[i] = new Button(this);
            //code from https://stackoverflow.com/a/20512925
            itemList[i].setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, 50));
            itemList[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            itemList[i].setText(queriedItem.get(i+1).getName());
            itemList[i].setTag(i+1);//i+1 is the referencing number in the arraylist
            itemList[i].setOnClickListener(btnClicked);
            frame.addView(itemList[i]);

        }

    }



}