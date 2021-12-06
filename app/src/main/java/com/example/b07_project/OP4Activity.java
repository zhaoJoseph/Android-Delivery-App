package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.b07_project.Model.ItemData;
import com.example.b07_project.Model.ItemDescriptionData;
import com.example.b07_project.Model.OrderData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OP4Activity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private OP4Adapter adapter;
    private RecyclerView.LayoutManager layout_manager;
    private List<OrderData> orders_list = new ArrayList<>();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_4);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // FIREBASE - Get orders_list for Owner

        temp_build_orders_list();

        recycler_view = findViewById(R.id.OP4RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layout_manager);
        adapter = new OP4Adapter((ArrayList<OrderData>) orders_list);
        recycler_view.setAdapter(adapter);

        adapter.setOnItemClickListener(new OP4Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), OP5Activity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        mDatabase.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders_list.clear();
                for(DataSnapshot children: snapshot.getChildren()){
                    OrderData o = children.getValue(OrderData.class);
                    if(o==null||o.getItems()==null)return;
                    if(!o.getIsComplete()&&o.getOrderingFrom().equals(mAuth.getCurrentUser().getUid())){
                        orders_list.add(o);
                    }
                }
                adapter = new OP4Adapter((ArrayList<OrderData>) orders_list);
                recycler_view.setAdapter(adapter);

                adapter.setOnItemClickListener(new OP4Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getApplicationContext(), OP5Activity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("customer_id",orders_list.get(position).getOrderBy());
                        intent.putExtra("order_id",orders_list.get(position).getOrderID());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        EditText search_bar = findViewById(R.id.OP4_search);
        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.owner_bottomNavigationView);
        nav.setSelectedItemId(R.id.owner_navigation_orders);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.owner_navigation_store:
                        Intent storeIntent = new Intent(OP4Activity.this, OP1Activity.class);
                        startActivity(storeIntent);
                        break;
                    case R.id.owner_navigation_orders:
                        Intent orderIntent = new Intent(OP4Activity.this, OP4Activity.class);
                        startActivity(orderIntent);
                        break;
                    case R.id.owner_navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(OP4Activity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }

    public void temp_build_orders_list() {
        orders_list = new ArrayList<>();
        /*
        code snippet for testing
        ItemDescriptionData Kitkat = new ItemDescriptionData("Kitkat", "Nestle", 0.99);
        ItemDescriptionData Twix = new ItemDescriptionData("Twix", "Mars Inc.", 12.99);
        ItemDescriptionData Mars = new ItemDescriptionData("Mars", "Mars Inc.", 1234.69);
        ItemDescriptionData Reese = new ItemDescriptionData("Reese's Peanut Butter Cup", "Hershey", 0.99);
        ItemDescriptionData Snickers = new ItemDescriptionData("Snickers", "Mars Inc.", 12.99);
        ItemDescriptionData Galaxy = new ItemDescriptionData("Galaxy", "Cadbury", 123.69);
        ItemDescriptionData Cadbury = new ItemDescriptionData("Cadbury", "Nestle", 0.99);
        ItemDescriptionData Hershey = new ItemDescriptionData("Hershey's Bar", "Hershey", 12.99);
        ItemDescriptionData Godiva = new ItemDescriptionData("Godiva Bar", "Godiva", 123.69);
        ItemDescriptionData Lindt = new ItemDescriptionData("Lindt", "Lindt",  0.99);
        ItemDescriptionData Ferrero = new ItemDescriptionData("Ferrero Rocher", "Ferrero", 12.99);
        ItemDescriptionData Aero = new ItemDescriptionData("Aero", "Nestle", 12.99);

        List<ItemData> items1 = new ArrayList<>();
        items1.add(new ItemData(Kitkat, 5));
        items1.add(new ItemData(Twix, 1));
        items1.add(new ItemData(Mars, 45));
        items1.add(new ItemData(Reese, 120));
        items1.add(new ItemData(Snickers, 69));
        items1.add(new ItemData(Galaxy, 59));
        items1.add(new ItemData(Cadbury, 1));
        items1.add(new ItemData(Hershey, 3));
        items1.add(new ItemData(Godiva, 2));
        items1.add(new ItemData(Lindt, 9));
        items1.add(new ItemData(Ferrero, 999));
        items1.add(new ItemData(Aero, 4));
        OrderData order_1 = new OrderData("CustomerID1", "OwnerID1", items1);
        order_1.setOrderID("1-3509");

        List<ItemData> items2 = new ArrayList<>();
        items2.add(new ItemData(Mars, 45));
        items2.add(new ItemData(Snickers, 69));
        items2.add(new ItemData(Cadbury, 1));
        items2.add(new ItemData(Godiva, 2));
        items2.add(new ItemData(Ferrero, 999));
        OrderData order_2 = new OrderData("CustomerID2", "OwnerID2", items2);
        order_2.setOrderID("2-9802");

        List<ItemData> items3 = new ArrayList<>();
        items3.add(new ItemData(Kitkat, 5));
        items3.add(new ItemData(Lindt, 9));
        OrderData order_3 = new OrderData("CustomerID3", "OwnerID3", items3);
        order_3.setOrderID("3-1098");

        orders_list.add(order_1);
        orders_list.add(order_2);
        orders_list.add(order_3);*/
    }

    private void filter(String text) {
        List<OrderData> filtered_list = new ArrayList<>();

        for (OrderData order : orders_list) {
            if (order.getOrderID().toLowerCase().contains(text.toLowerCase())) {
                filtered_list.add(order);
            }
        }

        adapter.filterList((ArrayList<OrderData>) filtered_list);
    }
}