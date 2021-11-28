package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OP2Activity extends AppCompatActivity {
    private Button add_item_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_page_2);

        add_item_button = findViewById(R.id.OP2_add_item_button);
        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cannot leave any field empty
                EditText et_item_name = findViewById(R.id.OP2_item_name_edit_text);
                String item_name = et_item_name.getText().toString();
                if (item_name.matches("")){
                    Toast.makeText(getApplicationContext(), "Invalid Item Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText et_brand_name = findViewById(R.id.OP2_brand_name_edit_text);
                String brand_name = et_brand_name.getText().toString();
                if (brand_name.matches("")){
                    Toast.makeText(getApplicationContext(), "Invalid Brand Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText et_price = findViewById(R.id.OP2_price_edit_text);
                String price = et_price.getText().toString();
                if (!price.matches("^\\d+\\.\\d{2}$")){
                    Toast.makeText(getApplicationContext(), "Invalid Price", Toast.LENGTH_SHORT).show();
                    return;
                }

                // FIREBASE - Add item to shop

                finish();
            }
        });

    }

}