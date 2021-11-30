package com.example.b07_project;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;

public class StoreActivity extends AppCompatActivity {

    private final String PREF_NAME = "pref_name";
    private ArrayAdapter adapter;
    private ArrayList<String> item_names = new ArrayList<>(Arrays.asList("item 1", "item 2", "item 3", "item 4", "item 5", "item 6"));
    private String store_name;
    private SharedPreferences store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        store = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        store_name = store.getString("pref_store", null);
        if(store_name == null) {
            store_name = getIntent().getStringExtra("store_name");
        }
        TextView store_text =  (TextView) findViewById(R.id.textView);
        store_text.setText(store_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView item_list = (ListView) findViewById(R.id.item_list);
        EditText filter = (EditText) findViewById(R.id.item_search_bar);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item_names);

        item_list.setAdapter(adapter);

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

        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoreActivity.this, ItemActivity.class);
                intent.putExtra("item_name", (String)adapter.getItem(position));
                store.edit().putString("pref_store", store_name).commit();
                startActivity(intent);

            }
        });

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
                        break;
                    case R.id.navigation_orders:
                        Intent orderIntent = new Intent(StoreActivity.this, CustomerOrderActivity.class);
                        startActivity(orderIntent);
                        break;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(StoreActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed(){
        SharedPreferences.Editor editor = store.edit();
        editor.clear().apply();
        editor.commit();
        super.onBackPressed();
    }

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