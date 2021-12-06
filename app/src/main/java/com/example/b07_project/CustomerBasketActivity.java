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

import com.example.b07_project.Model.ItemData;
import com.example.b07_project.Model.ItemDescriptionData;
import com.example.b07_project.Model.OrderData;
import com.example.b07_project.Model.ShopData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;



public class CustomerBasketActivity extends AppCompatActivity {

    private TableLayout table;
    private TextView price;
    private final ArrayList<OrderData> orderList = new ArrayList<>(Arrays.asList(new OrderData("Walmart", "John",new ArrayList<ItemData>(Arrays.asList(new ItemData(new ItemDescriptionData("Cookie","Chips Ahoy",3.99),5))))));
    private OrderData order = null;
    private final double Totalprice = 3.99;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //Todo FIREBASE: access the customers baskets and put all orders in orderlist

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_basket);
        String shop_name = getIntent().getExtras().getString("store");
        for(OrderData s: orderList){
            if(s.getOrderingFrom().equals(shop_name) && !s.getIsComplete()){
                order = new OrderData(s.getOrderingFrom(),s.getOrderID(), s.getItems());
                break;
            }
        }
        if(order != null) {
            addItems();
        }

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
        for(ItemData i: order.getItems()){
            TableRow row = new TableRow(this);
            itemDisplay item = new itemDisplay(this,null);
            row.addView(item);
            item.setName(i.getData().getName());
            item.setBrand(i.getData().getBrand());
            item.setQuantity(i.getQuantity());
            table.addView(row);
        }
        TextView price = (TextView) findViewById(R.id.totalPrice);
        price.setText("Price: $" +Double.toString(Totalprice));
        table.setStretchAllColumns(true);
    }

    public void complete_order(View view){
        //TODO firebase: finalize the order

        finish();
    }

}