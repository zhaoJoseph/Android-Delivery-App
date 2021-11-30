package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import AppClasses.Item;
import AppClasses.ItemDescription;
import AppClasses.Order;

public class OP4Activity extends AppCompatActivity {
    ArrayList<Order> orders_list;
    private RecyclerView recycler_view;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_4);

        // Firebase/other owner pages (get with intent?)
        // - Get data for owner (ie the orders_list)
        // TO FIGURE OUT!!!!!

        temp_build_orders_list(); // To remove when Firebase is implemented

        recycler_view = findViewById(R.id.OP4RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        adapter = new OP4Adapter(orders_list);

        recycler_view.setLayoutManager(layout_manager);
        recycler_view.setAdapter(adapter);

    }

    public void temp_build_orders_list() {
        orders_list = new ArrayList<>();

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

        Order order_1 = new Order("Customer 1", "Owner 1", 1);
        order_1.add_item(new Item(Kitkat, 5));
        order_1.add_item(new Item(Twix, 1));
        order_1.add_item(new Item(Mars, 45));
        order_1.add_item(new Item(Reese, 120));
        order_1.add_item(new Item(Snickers, 69));
        order_1.add_item(new Item(Galaxy, 59));
        order_1.add_item(new Item(Cadbury, 1));
        order_1.add_item(new Item(Hershey, 3));
        order_1.add_item(new Item(Godiva, 2));
        order_1.add_item(new Item(Lindt, 9));
        order_1.add_item(new Item(Ferrero, 999));
        order_1.add_item(new Item(Aero, 4));

        Order order_2 = new Order("Customer 2", "Owner 2", 2);
        order_2.add_item(new Item(Mars, 45));
        order_2.add_item(new Item(Snickers, 69));
        order_2.add_item(new Item(Cadbury, 1));
        order_2.add_item(new Item(Godiva, 2));
        order_2.add_item(new Item(Ferrero, 999));

        Order order_3 = new Order("Customer 3", "Owner 3", 3);
        order_3.add_item(new Item(Kitkat, 5));
        order_3.add_item(new Item(Lindt, 9));

        orders_list.add(order_1);
        orders_list.add(order_2);
        orders_list.add(order_3);
    }
}