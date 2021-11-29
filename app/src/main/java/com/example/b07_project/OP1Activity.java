package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import AppClasses.ItemDescription;
import AppClasses.Shop;

public class OP1Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OP1Adapter adapter;
    private RecyclerView.LayoutManager layout_manager;
    Shop shop;
    private Button add_item_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_1);

        // Firebase: Get User's shop data from database

        temp_build_shop();  // Remove function and function call once Firebase is implemented
        // shop = <from Firebase>

        build_recycler_view();

        add_item_button = findViewById(R.id.add_item_button);

        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open OP2
                Intent intent = new Intent(OP1Activity.this, OP2Activity.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemClickListener(new OP1Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Open OP3
                Intent intent = new Intent(getApplicationContext(), OP3Activity.class);

                Bundle bundle = new Bundle();
                bundle.putString("item_name", shop.getMenu().get(position).getName());
                bundle.putString("brand_name", shop.getMenu().get(position).getBrand());
                bundle.putDouble("price", shop.getMenu().get(position).getPrice());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        EditText search_bar = findViewById(R.id.OP1_search);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) { filter(s.toString()); }
        });
    }

    public void temp_build_shop() { // Remove once Firebase is implemented
        shop = new Shop("Walmart");

        shop.getMenu().add(new ItemDescription("Kitkat", 1, "Nestle", 0.99));
        shop.getMenu().add(new ItemDescription("Twix", 2, "Mars Inc.", 12.99));
        shop.getMenu().add(new ItemDescription("Mars", 3, "Mars Inc.", 1234.69));
        shop.getMenu().add(new ItemDescription("Reese's Peanut Butter Cup", 4, "Hershey", 0.99));
        shop.getMenu().add(new ItemDescription("Snickers", 5, "Mars Inc.", 12.99));
        shop.getMenu().add(new ItemDescription("Galaxy", 6, "Cadbury", 123.69));
        shop.getMenu().add(new ItemDescription("Cadbury", 7, "Nestle", 0.99));
        shop.getMenu().add(new ItemDescription("Hershey's Bar", 8, "Hershey", 12.99));
        shop.getMenu().add(new ItemDescription("Godiva Bar", 9, "Godiva", 123.69));
        shop.getMenu().add(new ItemDescription("Lindt", 10, "Lindt",  0.99));
        shop.getMenu().add(new ItemDescription("Ferrero Rocher", 11, "Ferrero", 12.99));
        shop.getMenu().add(new ItemDescription("Aero", 12, "Nestle", 12.99));
    }

    public void build_recycler_view() {
        TextView textview = (TextView)findViewById(R.id.OP1ShopName);
        textview.setText(shop.getName());

        recyclerView = (RecyclerView) findViewById(R.id.OP1RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        adapter = new OP1Adapter(shop.getMenu());

        recyclerView.setLayoutManager(layout_manager);
        recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        ArrayList<ItemDescription> filtered_list = new ArrayList<>();

        for (ItemDescription item : shop.getMenu()){
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filtered_list.add(item);
            }
        }

        adapter.filterList(filtered_list);
    }

}
