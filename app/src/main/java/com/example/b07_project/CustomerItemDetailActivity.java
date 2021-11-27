package com.example.b07_project;

//John Li

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CustomerItemDetailActivity extends AppCompatActivity {

    EditText quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_item_detail);
        TextView itemTitle = findViewById(R.id.item_name);
        TextView itemBrand = findViewById(R.id.item_brand);
        TextView itemPrice = findViewById(R.id.item_cost);

        itemTitle.setText(CustomerStoreItemListActivity.selectedItem.getName());

        EditText quantity = findViewById(R.id.number_of_id);
        //TODO please change this section to the quantity of item actually ordered
        //I can not do so currently as it requires the other section
        //you need to find the id first, then find the actual thing with the ID
        quantity.setText(0);
    }

    public void minusItem(View view){
        quantity.setText(Integer.parseInt(quantity.getText().toString())-1);
    }


    public void addItem(View view) {
        quantity.setText(Integer.parseInt(quantity.getText().toString())+1);
    }

    public void updateOrder(View view) {
        //TODO implement the functions to update the orders

    }
}