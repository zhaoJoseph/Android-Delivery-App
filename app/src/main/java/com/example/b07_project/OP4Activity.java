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

        temp_build_orders_list();

        // Build recycler view (the scrolling part of the page)
        recycler_view = findViewById(R.id.OP4RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layout_manager);
        adapter = new OP4Adapter((ArrayList<OrderData>) orders_list);
        recycler_view.setAdapter(adapter);

        // Add clicking functionality to each item (ie each order) on page
        adapter.setOnItemClickListener(new OP4Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), OP5Activity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        // Firebase - Get orders_list for Owner
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

                // Add clicking functionality to each item (ie each order) on page
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

        // Add search functionality to search bar
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

        // Create Navigation bar
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.owner_bottomNavigationView);
        nav.setSelectedItemId(R.id.owner_navigation_orders);

        // Add clicking functionality to Navigation bar
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.owner_navigation_store:
                        // Go to OP1
                        Intent storeIntent = new Intent(OP4Activity.this, OP1Activity.class);
                        startActivity(storeIntent);
                        break;
                    case R.id.owner_navigation_orders:
                        // Go to OP4
                        Intent orderIntent = new Intent(OP4Activity.this, OP4Activity.class);
                        startActivity(orderIntent);
                        break;
                    case R.id.owner_navigation_logout:
                        // Logout
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
    }

    private void filter(String text) {
        List<OrderData> filtered_list = new ArrayList<>();

        // Filter all orders to get searched order
        for (OrderData order : orders_list) {
            if (order.getOrderID().toLowerCase().contains(text.toLowerCase())) {
                filtered_list.add(order);
            }
        }

        adapter.filterList((ArrayList<OrderData>) filtered_list);
    }
}