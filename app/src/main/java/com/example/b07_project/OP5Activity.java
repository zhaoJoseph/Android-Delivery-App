package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07_project.Model.ItemData;
import com.example.b07_project.Model.ItemDescriptionData;
import com.example.b07_project.Model.OrderData;
import com.example.b07_project.Model.UserData;
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

public class OP5Activity extends AppCompatActivity {
    private RecyclerView recycler_view;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layout_manager;
    private Button mark_as_complete_button;
    private String order_id;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String customer_id;
    //List<OrderData> orders_list;
    List<ItemData> items_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_5);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();

        // Set order id on page
        TextView owner = findViewById(R.id.OP5_order_id);
        order_id = intent.getStringExtra("order_id");
        owner.setText(intent.getStringExtra("order_id"));

        // Set customer email on page
        TextView customer = findViewById(R.id.OP5_customer);
        customer_id = intent.getStringExtra("customer_id");
        mDatabase.child("users").child(customer_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData u = snapshot.getValue(UserData.class);
                if(u!=null&&u.getIsCustomer())
                customer.setText(u.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Build recycler view (the scrolling part of the page)
        recycler_view = findViewById(R.id.OP5RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layout_manager);

        adapter = new OP5Adapter(items_list);
        recycler_view.setAdapter(adapter);
        mDatabase.child("orders").child(order_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items_list.clear();
                OrderData o = snapshot.getValue(OrderData.class);
                if(o!=null&&o.getItems()!=null){
                    adapter = new OP5Adapter(o.getItems());
                    recycler_view.setAdapter(adapter);
                }else{
                    adapter = new OP5Adapter(items_list);
                    recycler_view.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Add clicking functionality to mark as complete button
        mark_as_complete_button = findViewById(R.id.OP5_mark_as_complete_button);
        mark_as_complete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firebase - Mark order as complete
                FirebaseDatabase.getInstance().getReference().child("orders").child(order_id).child("isComplete").setValue(true);
                finish();
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
                        Intent storeIntent = new Intent(OP5Activity.this, OP1Activity.class);
                        startActivity(storeIntent);
                        finish();
                        break;
                    case R.id.owner_navigation_orders:
                        // Go to OP4
                        Intent orderIntent = new Intent(OP5Activity.this, OP4Activity.class);
                        startActivity(orderIntent);
                        finish();
                        break;
                    case R.id.owner_navigation_logout:
                        // Logout
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(OP5Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

    }
}