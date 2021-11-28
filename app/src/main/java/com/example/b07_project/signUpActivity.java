package com.example.b07_project;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import encryption.MD5Encryption;

public class signUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //code from https://developer.android.com/guide/topics/ui/controls/spinner
        Spinner spinner = (Spinner) findViewById(R.id.accountTypeMenu);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.account_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    public void createAccount(View view){

        TextView emailText = findViewById(R.id.SignUpUserEmailAddress);
        TextView passwordText = findViewById(R.id.SignUpUserPassword);
        TextView confirmPasswordText = findViewById(R.id.signUpConfirmPassword);
        Spinner dropDownMenu = findViewById(R.id.accountTypeMenu);


        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        String accountType = dropDownMenu.getSelectedItem().toString();
        
        //https://www.javatpoint.com/java-email-validation
        String validEmail = ("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\" +
                ".[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");

        Pattern pattern = Pattern.compile(validEmail);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            emailText.setError("please enter a valid email address");

            return;
        }


        //TODO check if email is already in the account



        if (!password.equals(confirmPassword) ){
            confirmPasswordText.setError("does not equal password");
            confirmPasswordText.setText("");
            return;
        }

        if (password.equals("")){
            passwordText.setError("can not be empty");
            return;
        }

        if (password.length() < 6){
            passwordText.setError("must be longer than 6 characters!!!");
            return;
        }


        MD5Encryption encrypt = new MD5Encryption();
        String encryptedPassword = encrypt.encrypt(password);

        //TODO add the encrypted password and email as a new account
    }
}