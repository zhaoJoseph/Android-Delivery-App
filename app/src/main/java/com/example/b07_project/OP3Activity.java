package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OP3Activity extends AppCompatActivity {
    private Button delete_item_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_3);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
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

                finish();
            }
        });
    }

}
