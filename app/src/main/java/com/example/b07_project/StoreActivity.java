package com.example.b07_project;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.b07_project.Model.ItemData;
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
import java.util.Arrays;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    private final String PREF_NAME = "pref_name";
    private ArrayAdapter adapter;
    private List<ItemDescriptionData> items = new ArrayList<>();
    private ArrayList<String> item_names = new ArrayList<>();

    //TODO firebase: pull the shopdata from the database
    private String store_name;
    private String store_id;
    private DatabaseReference mDatabase;
    private SharedPreferences store;
    private RecyclerView recyclerView;
    private OP1Adapter adapter1;
    private RecyclerView.LayoutManager layout_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_store);
        //initialize variables and add preferences to remember which shop we are in
        store = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        store_name = store.getString("pref_store", null);
        store_id = store.getString("pref_store_id",null);
        if(store_name == null || store_id == null) {
            store_name = getIntent().getStringExtra("store_name");
            store_id = getIntent().getStringExtra("owner_id");
        }
        TextView store_text =  (TextView) findViewById(R.id.textView);
        //build dummy shop incase firebase error
        store_text.setText(store_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView item_list = (ListView) findViewById(R.id.item_list);
        EditText filter = (EditText) findViewById(R.id.item_search_bar);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_names);
        item_list.setAdapter(adapter);

        //build shop using firebase
        if(store_id!=null)
            mDatabase.child("shops").child(store_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    item_names.clear();
                    items.clear();
                    ShopData shop = snapshot.getValue(ShopData.class);
                    if(shop.getItems()!=null)
                        items = shop.getItems();
                    for(ItemDescriptionData i:items){
                        item_names.add(i.getName());
                    }
                    adapter = new ArrayAdapter<String>(StoreActivity.this, android.R.layout.simple_list_item_1, item_names);
                    item_list.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        //search functionality to shop
        filter.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (StoreActivity.this).adapter.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        //onclick activity for each item
        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                String item_name = (String)adapter.getItem(position);
                intent.putExtra("item_name", item_name);
                intent.putExtra("store_id",store_id);
                //remember preferences
                store.edit().putString("pref_store", store_name).commit();
                store.edit().putString("pref_store_id",store_id).commit();
                //build item by polling database about item info
                mDatabase.child("shops").child(store_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ShopData s = snapshot.getValue(ShopData.class);
                        ItemDescriptionData dummy = new ItemDescriptionData(item_name,"",0);
                        if(s!=null&&s.getItems()!=null&&s.getItems().contains(dummy)){
                            int index = s.getItems().indexOf(new ItemDescriptionData(item_name,"",0.0));
                            if(index != -1 ){
                                intent.putExtra("brand",s.getItems().get(index).getBrand());
                                intent.putExtra("price",s.getItems().get(index).getPrice().toString());
                                startActivity(intent);
                            }}
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        //navigation bar
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        nav.getMenu().setGroupCheckable(0, false,true);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nav.getMenu().setGroupCheckable(0, true,true);
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_stores:
                        Intent storeIntent = new Intent(StoreActivity.this, StoreListActivity.class);
                        startActivity(storeIntent);
                        finish();
                        break;
                    case R.id.navigation_orders:
                        Intent orderIntent = new Intent(StoreActivity.this, CustomerOrderActivity.class);
                        startActivity(orderIntent);
                        finish();
                        break;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(StoreActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

    }

    //back button functionality
    @Override
    public void onBackPressed(){
        SharedPreferences.Editor editor = store.edit();
        editor.clear().apply();
        editor.commit();
        super.onBackPressed();
    }
    //basked functionality
    public void openBasket(View view){
        Intent basket = new Intent(StoreActivity.this, CustomerBasketActivity.class);
        basket.putExtra("store", store_name);
        basket.putExtra("store_id",store_id);
        store.edit().putString("pref_store", store_name).commit();
        store.edit().putString("pref_store_id",store_id).commit();
        startActivity(basket);
    }
    //add back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}