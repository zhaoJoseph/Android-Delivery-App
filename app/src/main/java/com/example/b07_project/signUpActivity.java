package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.b07_project.Model.ItemDescriptionData;
import com.example.b07_project.Model.ShopData;
import com.example.b07_project.Model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    public void launch_page(boolean IsCustomer){
        Intent intent;
        if(IsCustomer){
             intent = new Intent(this, StoreListActivity.class);
        }
        else{
             intent = new Intent(this, OP1Activity.class);
        }
        startActivity(intent);


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

        //use firebase to create an account
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            UserData u = new UserData(email,accountType.equals("Customer Account"));
                            updateDatabase(u);
                            launch_page(u.getIsCustomer());
                        } else {
                            // If sign in fails, display a message to the user.
                            try {
                                Toast.makeText(signUpActivity.this, "Authentication failed." + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }catch (NullPointerException e){
                                Toast.makeText(signUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public void updateDatabase(UserData u){
        //write data
        String userID = mAuth.getCurrentUser().getUid();
        //write to database
        mDatabase.child("users").child(userID).setValue(u);
        //add a new shop to database if the user is not a customer
        if(!u.getIsCustomer()){
        //its an owner account thus create a shop for him to
            //temporarily add a new ite
            List<ItemDescriptionData> i = new ArrayList<>();
            ShopData s = new ShopData(userID,u.getEmail()+"'s Shop",i);
            mDatabase.child("shops").child(userID).setValue(s);
        }
    }
}