package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import encryption.MD5Encryption;

public class ItemActivity extends AppCompatActivity {
    private Button ordered;
    private Button add;
    private Button subtract;
    private TextView quantity;
    private int amount =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ordered = (Button)findViewById(R.id.order_button);
        add = (Button)findViewById(R.id.add_amount);
        subtract = (Button)findViewById(R.id.remove_amount);
        quantity = (TextView) findViewById(R.id.quant);

    }

    public void loginAccount(View view){
        TextView emailText = (TextView) findViewById(R.id.userEmailAddress);
        TextView passwordText = (TextView) findViewById(R.id.userPassword);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        MD5Encryption encrypt = new MD5Encryption();
        String encryptedPassword = encrypt.encrypt(password);

        //TODO check if encryptedPassword matches with data in the firebase, if yes go into the next page

        emailText.setText("");
        passwordText.setText("");

    }

    public void signupAccount(View view){

        Intent intent = new Intent(this, signUpActivity.class);

        startActivity(intent);
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
            this.finish();
        }
    }

}