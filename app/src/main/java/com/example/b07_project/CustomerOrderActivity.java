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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerOrderActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayList<String> item_names = new ArrayList<>(Arrays.asList("Order 1", "Order 2", "Order 3", "Order 4", "Order 5", "Order 6"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order);
        ListView id_list = (ListView) findViewById(R.id.order_list);
        EditText search = (EditText) findViewById(R.id.searchbar_order);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_names);
        id_list.setAdapter(adapter);
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

        id_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CustomerOrderIdActivity.class);
                String order = (String)adapter.getItem(position);
                intent.putExtra("order_id", order.replaceAll("[\\D]",""));
                startActivity(intent);

            }
        });

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_stores:
                        Intent orderIntent = new Intent(CustomerOrderActivity.this, StoreListActivity.class);
                        startActivity(orderIntent);
                        break;
                        case R.id.navigation_orders:
                            break;
                        case R.id.navigation_logout:
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(CustomerOrderActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                }
                return true;
            }
        }

        );
    }


}



