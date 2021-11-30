package com.example.b07_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import AppClasses.Item;
import AppClasses.ItemDescription;
import AppClasses.Order;

public class OP5Activity extends AppCompatActivity {
    Order order;
    private RecyclerView recycler_view;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_5);

        // Firebase/From data from other owner pages (through intent)

        // Temporary - For testing purposes
        build_content_list();

        TextView order_id = (TextView) findViewById(R.id.OP5_order_id);
        order_id.setText(String.valueOf(order.getID()));

        TextView customer = (TextView) findViewById(R.id.OP5_customer);
        customer.setText(order.getOrderCustomer());

        recycler_view = findViewById(R.id.OP5RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        adapter = new OP5Adapter(order.getContent());

        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

    }

    public void build_content_list(){
        ItemDescription Kitkat = new ItemDescription("Kitkat", 1, "Nestle", 0.99);
        ItemDescription Twix = new ItemDescription("Twix", 2, "Mars Inc.", 12.99);
        ItemDescription Mars = new ItemDescription("Mars", 3, "Mars Inc.", 1234.69);
        ItemDescription Reese = new ItemDescription("Reese's Peanut Butter Cup", 4, "Hershey", 0.99);
        ItemDescription Snickers = new ItemDescription("Snickers", 5, "Mars Inc.", 12.99);
        ItemDescription Galaxy = new ItemDescription("Galaxy", 6, "Cadbury", 123.69);
        ItemDescription Cadbury = new ItemDescription("Cadbury", 7, "Nestle", 0.99);
        ItemDescription Hershey = new ItemDescription("Hershey's Bar", 8, "Hershey", 12.99);
        ItemDescription Godiva = new ItemDescription("Godiva Bar", 9, "Godiva", 123.69);
        ItemDescription Lindt = new ItemDescription("Lindt", 10, "Lindt",  0.99);
        ItemDescription Ferrero = new ItemDescription("Ferrero Rocher", 11, "Ferrero", 12.99);
        ItemDescription Aero = new ItemDescription("Aero", 12, "Nestle", 12.99);

        order = new Order("Jake", "Alina", 39248);
        order.add_item(new Item(Kitkat, 5));
        order.add_item(new Item(Twix, 1));
        order.add_item(new Item(Mars, 45));
        order.add_item(new Item(Reese, 120));
        order.add_item(new Item(Snickers, 69));
        order.add_item(new Item(Galaxy, 59));
        order.add_item(new Item(Cadbury, 1));
        order.add_item(new Item(Hershey, 3));
        order.add_item(new Item(Godiva, 2));
        order.add_item(new Item(Lindt, 9));
        order.add_item(new Item(Ferrero, 999));
        order.add_item(new Item(Aero, 4));
    }

}

