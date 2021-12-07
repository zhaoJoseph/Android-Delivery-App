package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class OP2Activity extends AppCompatActivity {
    private Button add_item_button;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase_Shop_User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_2);
        mAuth = FirebaseAuth.getInstance();
        mDatabase_Shop_User = FirebaseDatabase.getInstance().getReference().child("shops").child(mAuth.getCurrentUser().getUid());

        // Add clicking functionality to Add Item button
        add_item_button = findViewById(R.id.OP2_add_item_button);
        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cannot leave any field empty
                EditText et_item_name = findViewById(R.id.OP2_item_name_edit_text);
                String item_name = et_item_name.getText().toString();
                if (item_name.matches("")){
                    // Validate Item Name - Should not be empty
                    Toast.makeText(getApplicationContext(), "Invalid Item Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText et_brand_name = findViewById(R.id.OP2_brand_name_edit_text);
                String brand_name = et_brand_name.getText().toString();
                if (brand_name.matches("")){
                    // Validate Brand Name - Should not be empty
                    Toast.makeText(getApplicationContext(), "Invalid Brand Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText et_price = findViewById(R.id.OP2_price_edit_text);
                String price = et_price.getText().toString();
                if (!price.matches("^\\d+\\.\\d{2}$")){
                    // Validate Price - should be of x.xx format
                    Toast.makeText(getApplicationContext(), "Invalid Price", Toast.LENGTH_SHORT).show();
                    return;
                }

                // FIREBASE - Add item to shop
                mDatabase_Shop_User.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ShopData my_shop = snapshot.getValue(ShopData.class);
                        if(my_shop.getItems()!=null)
                        {
                            if(!my_shop.getItems().contains(new ItemDescriptionData(item_name,"",0.0)))
                            {
                                my_shop.getItems().add(new ItemDescriptionData(item_name, brand_name, Double.parseDouble(price)));
                                mDatabase_Shop_User.setValue(my_shop);
                            }
                            else{
                                Toast.makeText(OP2Activity.this, "Shop already contains this item", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            mDatabase_Shop_User.child("items").child("0").setValue(new ItemDescriptionData(item_name,brand_name,Double.parseDouble(price)));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                finish();
            }
        });

        // Create Navigation bar
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.owner_bottomNavigationView);
        nav.setSelectedItemId(R.id.owner_navigation_store);

        // Add clicking functionality to Navigation bar
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.owner_navigation_store:
                        // Go to OP1
                        Intent storeIntent = new Intent(OP2Activity.this, OP1Activity.class);
                        startActivity(storeIntent);
                        finish();
                        break;
                    case R.id.owner_navigation_orders:
                        // Go to OP4
                        Intent orderIntent = new Intent(OP2Activity.this, OP4Activity.class);
                        startActivity(orderIntent);
                        finish();
                        break;
                    case R.id.owner_navigation_logout:
                        // Logout
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(OP2Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

    }

}