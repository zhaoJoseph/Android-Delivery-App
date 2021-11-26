package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import AppClasses.Item;
import AppClasses.ItemDescription;

public class CustomerStoreItemListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_store_item_list);
        ArrayList<ItemDescription> listOfItem = CustomerStoreListActivity.selected.getMenu();

    }
}