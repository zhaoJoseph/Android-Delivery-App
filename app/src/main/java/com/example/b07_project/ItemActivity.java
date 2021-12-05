package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.b07_project.Model.ItemDescriptionData;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity {
    private Button ordered;
    private Button add;
    private Button subtract;
    private TextView quantity;
    private TextView itemName;
    private TextView itemPrice;
    private TextView itemBrand;
    private String store_id;
    private String item;
    private String brand;
    private String price;
    private int amount =0;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ordered = (Button)findViewById(R.id.order_button);
        add = (Button)findViewById(R.id.add_amount);
        subtract = (Button)findViewById(R.id.remove_amount);
        quantity = (TextView) findViewById(R.id.quant);

        itemName = (TextView) findViewById(R.id.item_name);
        itemBrand = (TextView) findViewById(R.id.item_brand);
        itemPrice = (TextView) findViewById(R.id.item_cost);
         store_id = getIntent().getExtras().getString("store_id");
         item = getIntent().getExtras().getString("item_name");
         brand = getIntent().getExtras().getString("brand");
         price = getIntent().getExtras().getString("price");



        itemName.setText(item);
        itemPrice.setText(price);
        itemBrand.setText(brand);


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
            if(store_id!=null)
            mDatabase.child("basket").child(mAuth.getCurrentUser().getUid()).child(store_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot==null){
                        //create new order and add to it

                    }else{
                        //add to existing order
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            this.finish();
        }
        if(amount == 0){
            //only remove if order exists and contains the item
        }

    }

}