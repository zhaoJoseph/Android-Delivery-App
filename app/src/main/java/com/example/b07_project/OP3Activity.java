package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_project.Model.ItemDescriptionData;
import com.example.b07_project.Model.ShopData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OP3Activity extends AppCompatActivity {
    private Button delete_item_button;
    private FirebaseAuth mAuth;
    private Integer position;
    private DatabaseReference mDatabase_Shop_User;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_3);
        mAuth = FirebaseAuth.getInstance();
        mDatabase_Shop_User = FirebaseDatabase.getInstance().getReference().child("shops").child(mAuth.getCurrentUser().getUid());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            position = bundle.getInt("position");
            String item_name = bundle.getString("item_name");
            String brand_name = bundle.getString("brand_name");
            Double price = bundle.getDouble("price");

            TextView item_name_tv = findViewById(R.id.OP3_item_name);
            TextView brand_name_tv = findViewById(R.id.OP3_brand_name);
            TextView price_tv = findViewById(R.id.OP3_price);

            item_name_tv.setText(item_name);
            brand_name_tv.setText(brand_name);
            price_tv.setText(String.valueOf(price));
        }

        delete_item_button = findViewById(R.id.OP3_delete_item_button);
        delete_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FIREBASE - Delete item from shop
                mDatabase_Shop_User.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ShopData my_shop = snapshot.getValue(ShopData.class);
                        Toast.makeText(OP3Activity.this,my_shop.getItems().get(position).getName(),Toast.LENGTH_LONG).show();
                        List<ItemDescriptionData> temp = new ArrayList<>();
                        int counter =0;
                        for(ItemDescriptionData i:my_shop.getItems()){
                            if(counter!=position)
                            temp.add(i);
                            counter++;
                        }
                        if(!temp.isEmpty()) {
                            mDatabase_Shop_User.child("items").setValue(temp);
                        }else{
                            mDatabase_Shop_User.child("items").removeValue();
                        }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                finish();
            }
        });
    }

}
