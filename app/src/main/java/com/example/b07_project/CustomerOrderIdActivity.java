package com.example.b07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.b07_project.Model.ItemData;
import com.example.b07_project.Model.ItemDescriptionData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import AppClasses.Item;
import AppClasses.ItemDescription;

public class CustomerOrderIdActivity extends AppCompatActivity {
    private TableLayout order;
    private TextView storeName;
    private TextView status;
    private TextView OrderId;
    private TextView price;
    private final double TotalPrice = 3.99;
    private final ArrayList<ItemData> orderList = new ArrayList<>(Arrays.asList(new ItemData(new ItemDescriptionData("Cookie","Chips Ahoy",3.99),2)));
    private final String store = "Walmart";
    //TODO firebase: access the store name
    private final String stat = "Ready for Pickup";
    //TODO firebase: access the status of the store

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_id);
        storeName = (TextView) findViewById(R.id.shop_name);
        status = (TextView) findViewById(R.id.status_name);
        OrderId = (TextView) findViewById(R.id.order_id_title);
        storeName.setText("Store: " + store);
        status.setText("Status: " + stat);
        Bundle id = getIntent().getExtras();
        OrderId.setText("Order ID: " +id.getString("order_id"));
        addItems();

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
                        break;
                    case R.id.navigation_orders:
                        Intent orderIntent = new Intent(CustomerOrderIdActivity.this, CustomerOrderActivity.class);
                        startActivity(orderIntent);
                        break;
                    case R.id.navigation_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(CustomerOrderIdActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

    public void addItems(){
        order = (TableLayout) findViewById(R.id.table_order);
        for(ItemData i: orderList){
            TableRow row = new TableRow(this);
            itemDisplay item = new itemDisplay(this,null);
            row.addView(item);
            item.setName(i.getData().getName());
            item.setBrand(i.getData().getBrand());
            item.setQuantity(i.getQuantity());
            order.addView(row);
        }
        price = (TextView) findViewById(R.id.recieptPrice);
        price.setText("Price: $" +Double.toString(TotalPrice));
        order.setStretchAllColumns(true);
    }
}