package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class StoreListActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private ArrayList<String> store_names = new ArrayList<>(Arrays.asList("Store 1", "Store 2", "Store 3", "Store 4", "Store 5", "Store 6"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);
        ListView store_list = (ListView) findViewById(R.id.store_items);
        EditText filter = (EditText) findViewById(R.id.search_bar);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, store_names);

        store_list.setAdapter(adapter);

        filter.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (StoreListActivity.this).adapter.getFilter().filter(charSequence);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        store_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("store_name", (String)adapter.getItem(position));
                startActivity(intent);

            }
        });

    }
}