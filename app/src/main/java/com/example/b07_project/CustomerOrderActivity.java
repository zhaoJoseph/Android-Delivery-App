package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.b07_project.Model.OrderData;
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
import java.util.Arrays;

public class CustomerOrderActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayList<String> order_names = new ArrayList<>();
    private ArrayList<OrderData> orders= new ArrayList<>();
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize variables
        setContentView(R.layout.activity_customer_order);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ListView id_list = (ListView) findViewById(R.id.order_list);
        EditText search = (EditText) findViewById(R.id.searchbar_order);
        //makes a view incase firebase code fails
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, order_names);
        id_list.setAdapter(adapter);

        //search functionality
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (CustomerOrderActivity.this).adapter.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });
        //query firebase and build orders
        mDatabase.child("orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                order_names.clear();
                for(DataSnapshot children: snapshot.getChildren()){
                    OrderData o = children.getValue(OrderData.class);
                    if(o==null)return;
                    if(o.getOrderBy().equals(mAuth.getCurrentUser().getUid())){
                        orders.add(o);
                        String temp = "Order#"+o.getOrderID();
                        if(o.getIsComplete())temp="[COMPLETE] "+temp;
                        order_names.add(temp);
                    }
                }
                adapter = new ArrayAdapter<String>(CustomerOrderActivity.this, android.R.layout.simple_list_item_1, order_names);
                id_list.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Add listner to each order in view
        id_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CustomerOrderIdActivity.class);
                intent.putExtra("order_id", orders.get(position).getOrderID());
                String status = "Not Complete";
                if(orders.get(position).getIsComplete())
                    status="Ready to Pickup";
                intent.putExtra("Status",status);
                //get shop name and put it into the intent and call activity
                mDatabase.child("shops").child(orders.get(position).getOrderingFrom()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ShopData s = snapshot.getValue(ShopData.class);
                        if(s!=null)
                        {
                            intent.putExtra("name",s.getShop_name());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        //botton navigation bar
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        nav.getMenu().setGroupCheckable(0, false,true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nav.getMenu().setGroupCheckable(0, true,true);
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_stores:
                        Intent orderIntent = new Intent(CustomerOrderActivity.this, StoreListActivity.class);
                        startActivity(orderIntent);
                        finish();
                        break;
                        case R.id.navigation_orders:
                            break;
                        case R.id.navigation_logout:
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(CustomerOrderActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                }
                return true;
            }
        }

        );
    }


}



