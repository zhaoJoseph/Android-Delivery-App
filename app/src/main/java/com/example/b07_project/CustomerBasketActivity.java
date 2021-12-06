package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.voice.VoiceInteractionSession;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07_project.Model.ItemData;
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
import java.util.Arrays;



public class CustomerBasketActivity extends AppCompatActivity {

    private TableLayout table;
    private TextView price;
   // private final ArrayList<OrderData> orderList = new ArrayList<>(Arrays.asList(new OrderData("Walmart", "John",new ArrayList<ItemData>(Arrays.asList(new ItemData(new ItemDescriptionData("Cookie","Chips Ahoy",3.99),5))))));
    private OrderData order = null;
    //private final double Totalprice = 3.99;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String store_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_basket);
        mAuth = FirebaseAuth.getInstance();
        store_id = getIntent().getExtras().getString("store_id");
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("basket").child(mAuth.getCurrentUser().getUid()).child(store_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                order = snapshot.getValue(OrderData.class);
                if(order!=null&&order.getItems()!=null)
                {addItems();}
                else{
                    order = new OrderData("","",new ArrayList<>());
                    addItems();
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_stores:
                        Intent storeIntent = new Intent(CustomerBasketActivity.this, StoreListActivity.class);
                        startActivity(storeIntent);
                        break;
                    case R.id.navigation_orders:
                        Intent orderIntent = new Intent(CustomerBasketActivity.this, CustomerOrderActivity.class);
                        startActivity(orderIntent);
                        break;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(CustomerBasketActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    public void addItems(){
        table = (TableLayout) findViewById(R.id.items_table);
        Double Totalprice = 0.0;
        for(ItemData i: order.getItems()){
            TableRow row = new TableRow(this);
            itemDisplay item = new itemDisplay(this,null);
            row.addView(item);
            item.setName(i.getData().getName());
            item.setBrand(i.getData().getBrand());
            item.setQuantity(i.getQuantity());
            Totalprice+=i.getQuantity()*i.getData().getPrice();
            table.addView(row);
        }
        TextView price = (TextView) findViewById(R.id.totalPrice);
        price.setText("Price: $" +Double.toString((double)Math.round(Totalprice * 1000d) / 1000d));
        table.setStretchAllColumns(true);
    }

    public void complete_order(View view){
        mDatabase.child("basket").child(mAuth.getCurrentUser().getUid()).child(store_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderData o = snapshot.getValue(OrderData.class);
                if(o!=null&&o.getItems()!=null){
                    String k = mDatabase.child("orders").push().getKey();
                    mDatabase.child("orders").child(k).setValue(o);
                    mDatabase.child("orders").child(k).child("orderID").setValue(k);
                    mDatabase.child("basket").child(mAuth.getCurrentUser().getUid()).child(store_id).child("items").removeValue();
                }else{
                    Toast.makeText(CustomerBasketActivity.this, "No items in Basket Cant place order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        finish();
    }

}