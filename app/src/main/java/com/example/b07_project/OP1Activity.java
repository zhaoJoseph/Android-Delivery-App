package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    // Add onResume so that when you move between pages, it updates?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_1);

        // Firebase: Get items as ArrayList, and Shop Name from Shop

        // shop = get from firebase
        // String shop_name = shop.getName();
        // ArrayList<ItemDescription> items = shop.getMenu();

        // Temporary - For testing purposes
        String shop_name = "Walmart";
        ArrayList<ItemDescription> items = new ArrayList<ItemDescription>();
        items.add(new ItemDescription("Kitkat", 1, "Nestle", 0.99));
        items.add(new ItemDescription("Twix", 2, "Mars Inc.", 12.99));
        items.add(new ItemDescription("Mars", 3, "Mars Inc.", 123.69));
        items.add(new ItemDescription("Kitkat", 4, "Nestle", 0.99));
        items.add(new ItemDescription("Twix", 5, "Mars Inc.", 12.99));
        items.add(new ItemDescription("Mars", 6, "Mars Inc.", 123.69));
        items.add(new ItemDescription("Kitkat", 7, "Nestle", 0.99));
        items.add(new ItemDescription("Twix", 8, "Mars Inc.", 12.99));
        items.add(new ItemDescription("Mars", 9, "Mars Inc.", 123.69));
        items.add(new ItemDescription("Kitkat", 10, "Nestle", 0.99));
        items.add(new ItemDescription("Twix", 11, "Mars Inc.", 12.99));
        items.add(new ItemDescription("Mars", 12, "Mars Inc.", 123.69));
        // End of temporary

        TextView textview = findViewById(R.id.OP1ShopName);
        textview.setText(shop_name);

        recyclerView = findViewById(R.id.OP1RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        adapter = new OP1Adapter(items);

        recyclerView.setLayoutManager(layout_manager);
        recyclerView.setAdapter(adapter);

        add_item_button = findViewById(R.id.add_item_button);

        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_OP2();
            }
        });

        adapter.setOnItemClickListener(new OP1Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), OP3Activity.class);

                Bundle bundle = new Bundle();
                bundle.putString("item_name", items.get(position).getName());
                bundle.putString("brand_name", items.get(position).getBrand());
                bundle.putDouble("price", items.get(position).getPrice());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void open_OP2() {
        Intent intent = new Intent(OP1Activity.this, OP2Activity.class);
        startActivity(intent);
    }


}
