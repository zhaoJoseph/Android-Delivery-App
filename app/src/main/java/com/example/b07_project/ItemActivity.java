package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class ItemActivity extends AppCompatActivity {
    private Button ordered;
    private Button add;
    private Button subtract;
    private TextView quantity;
    private int amount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ordered = (Button)findViewById(R.id.order_button);
        add = (Button)findViewById(R.id.add_amount);
        subtract = (Button)findViewById(R.id.remove_amount);
        quantity = (TextView) findViewById(R.id.quant);

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        nav.getMenu().setGroupCheckable(0, false,true);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nav.getMenu().setGroupCheckable(0, true,true);
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_stores:
                        Intent storeIntent = new Intent(ItemActivity.this, StoreListActivity.class);
                        startActivity(storeIntent);
                        break;
                    case R.id.navigation_orders:
                        Intent orderIntent = new Intent(ItemActivity.this, CustomerOrderActivity.class);
                        startActivity(orderIntent);
                        break;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(ItemActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }


    public void edit_quantity(View v){
        if(v == add){
                amount++;
        }else if(v == subtract){
            if(amount > 0){
                amount--;
            }
        }
        quantity.setText(Integer.toString(amount));
    }

    public void added_to_order(View v){
       //add to order
        if(amount != 0) {
            this.finish();
        }
        if(amount == 0){
            //TODO remove the item from the list in the firebase
        }

    }

}