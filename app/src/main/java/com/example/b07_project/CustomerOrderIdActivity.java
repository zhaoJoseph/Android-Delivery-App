package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.b07_project.Model.ItemData;
import com.example.b07_project.Model.ItemDescriptionData;
import com.example.b07_project.Model.OrderData;
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



public class CustomerOrderIdActivity extends AppCompatActivity {
    private TableLayout order;
    private TextView storeName;
    private TextView status;
    private TextView OrderId;
    private TextView price;
    private OrderData my_order = null;
    private String order_id = "";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    //TODO firebase: access the status of the order

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_id);
        //initialize variables
        storeName = (TextView) findViewById(R.id.shop_name);
        status = (TextView) findViewById(R.id.status_name);
        OrderId = (TextView) findViewById(R.id.order_id_title);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bundle id = getIntent().getExtras();
        //build order details
        storeName.setText("Store: " + id.getString("name"));
        status.setText("Status: " + id.getString("Status"));
        OrderId.setText("Order ID: " +id.getString("order_id"));
        order_id = id.getString("order_id");


        Button completeOrder = (Button) findViewById(R.id.orderComplete);
        //query database for order and update button as needed
        mDatabase.child("orders").child(order_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                my_order = snapshot.getValue(OrderData.class);
                if(my_order!=null&&my_order.getItems()!=null)
                {
                    addItems();
                    completeOrder.setVisibility(View.INVISIBLE);
                    if(my_order.getIsComplete())
                    {
                        completeOrder.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    my_order = new OrderData("","",new ArrayList<>());
                    completeOrder.setVisibility(View.INVISIBLE);
                    addItems();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //add navigation bar
        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        nav.getMenu().setGroupCheckable(0, false,true);


        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                nav.getMenu().setGroupCheckable(0, true,true);
                int id = item.getItemId();
                switch(id){
                    case R.id.navigation_stores:
                        Intent storeIntent = new Intent(CustomerOrderIdActivity.this, StoreListActivity.class);
                        startActivity(storeIntent);
                        finish();
                        break;
                    case R.id.navigation_orders:
                        Intent orderIntent = new Intent(CustomerOrderIdActivity.this, CustomerOrderActivity.class);
                        startActivity(orderIntent);
                        finish();
                        break;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(CustomerOrderIdActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    //build order items
    public void addItems(){
        order = (TableLayout) findViewById(R.id.table_order);
        Double Totalprice = 0.0;
        for(ItemData i: my_order.getItems()){
            TableRow row = new TableRow(this);
            itemDisplay item = new itemDisplay(this,null);
            row.addView(item);
            item.setName(i.getData().getName());
            item.setBrand(i.getData().getBrand());
            item.setQuantity(i.getQuantity());
            Totalprice+=i.getQuantity()*i.getData().getPrice();
            order.addView(row);
        }
        price = (TextView) findViewById(R.id.receiptPrice);
        price.setText("Price: $" +Double.toString((double)Math.round(Totalprice * 1000d) / 1000d));
        order.setStretchAllColumns(true);
    }

    //remove order from firebase
    public void completeOrder(View view){
        mDatabase.child("orders").child(order_id).removeValue();
        finish();

    }

}