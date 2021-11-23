package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerOrderActivity extends AppCompatActivity {
// implement infinite scroll using custom adapter with the recyclerview later

    private ArrayAdapter adapter;
    private ArrayList<String> item_names = new ArrayList<>(Arrays.asList("item 1", "item 2", "item 3", "item 4", "item 5", "item 6"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);
        ListView item_list = (ListView) findViewById(R.id.order_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_names);
        item_list.setAdapter(adapter);
    }


}



