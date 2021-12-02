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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07_project.Model.ItemDescriptionData;
import com.example.b07_project.Model.ShopData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import AppClasses.ItemDescription;
import AppClasses.Shop;

public class OP1Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OP1Adapter adapter;
    private RecyclerView.LayoutManager layout_manager;
   // Shop shop;
     ShopData my_shop;
    private ArrayList<String> ss = new ArrayList<>();
    private Button add_item_button;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase_Shop_User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_1);
        mAuth = FirebaseAuth.getInstance();
        mDatabase_Shop_User = FirebaseDatabase.getInstance().getReference();
        // Firebase: Get User's shop data from database
        temp_build_shop("Temp");  // Remove function and function call once Firebase is implemented
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
                bundle.putString("item_name", my_shop.getItems().get(position).getName());
                bundle.putString("brand_name", my_shop.getItems().get(position).getBrand());
                bundle.putDouble("price", my_shop.getItems().get(position).getPrice());
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mDatabase_Shop_User.child("shops").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                my_shop = snapshot.getValue(ShopData.class);
                if(my_shop.getItems()==null){
                    temp_build_shop(my_shop.getShop_name());
                }
                build_recycler_view();
                adapter.setOnItemClickListener(new OP1Adapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Open OP3
                        Intent intent = new Intent(getApplicationContext(), OP3Activity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("item_name", my_shop.getItems().get(position).getName());
                        bundle.putString("brand_name", my_shop.getItems().get(position).getBrand());
                        bundle.putDouble("price", my_shop.getItems().get(position).getPrice());
                        bundle.putInt("position",position);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.owner_bottomNavigationView);
        nav.setSelectedItemId(R.id.owner_navigation_store);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.owner_navigation_store:
                        Intent storeIntent = new Intent(OP1Activity.this, OP1Activity.class);
                        startActivity(storeIntent);
                        break;
                    case R.id.owner_navigation_orders:
                        Intent orderIntent = new Intent(OP1Activity.this, OP4Activity.class);
                        startActivity(orderIntent);
                        break;
                    case R.id.owner_navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(OP1Activity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    public void temp_build_shop(String name) { // Remove once Firebase is implemented
        //shop = new Shop("Walmart");
        my_shop = new ShopData(mAuth.getCurrentUser().getUid(),name,new ArrayList<ItemDescriptionData>());

    }

    public void build_recycler_view() {
        TextView textview = (TextView)findViewById(R.id.OP1ShopName);
        textview.setText(my_shop.getShop_name());
        //textview.setText(my_shop.getShop_name(););

        recyclerView = (RecyclerView) findViewById(R.id.OP1RecyclerView);
        layout_manager = new LinearLayoutManager(this);
        //Convert HashMap to ArrayList
        /*
        Collection<Integer> values = map.values();

        // Creating an ArrayList of values
        ArrayList<Integer> listOfValues
                = new ArrayList<>(values);
       */
        adapter = new OP1Adapter(my_shop.getItems());

        recyclerView.setLayoutManager(layout_manager);
        recyclerView.setAdapter(adapter);
    }

    private void filter(String text) {
        ArrayList<ItemDescriptionData> filtered_list = new ArrayList<>();

        for (ItemDescriptionData item : my_shop.getItems()){
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filtered_list.add(item);
            }
        }

        adapter.filterList(filtered_list);
    }

}
