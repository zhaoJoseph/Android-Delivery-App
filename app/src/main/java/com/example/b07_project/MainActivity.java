package com.example.b07_project;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07_project.Model.LoginModel;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements Contract.View{
    private FirebaseAuth mAuth;
    private Contract.presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        presenter = new LoginPresenter( new LoginModel(), this);
        setContentView(R.layout.activity_main);

    }
    @Override
    public void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
            mAuth.signOut();
    }
    @Override
    public void onBackPressed(){
        if (mAuth.getCurrentUser()!=null)
            super.onBackPressed();
        //prevent users from going back to pages where you'd need to
        //be logged in
    }

    @Override
    public void launch_page_customer(){
        Intent intent = new Intent(this, StoreListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void launch_page_owner(){
        Intent intent = new Intent(this, OP1Activity.class);
        startActivity(intent);
        finish();
    }


    public void loginAccount(View view){
        presenter.attemptLogin();
    }


    public void signupAccount(View view){
        Intent intent = new Intent(this, signUpActivity.class);
        startActivity(intent);
    }

    @Override
    public String getEmail() {
        TextView emailText = (TextView) findViewById(R.id.userEmailAddress);
        String email = emailText.getText().toString();

        return email;
    }

    @Override
    public String getPassword() {
        TextView passwordText = (TextView) findViewById(R.id.userPassword);
        String password = passwordText.getText().toString();

        return password;
    }

    @Override
    public void displayError(String errorMessage) {
        Toast.makeText(MainActivity.this, errorMessage,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayEmailError(String errorMessage) {
        TextView emailText = (TextView) findViewById(R.id.userEmailAddress);
        emailText.setError(errorMessage);
    }

    @Override
    public void displayPasswordError(String errorMessage) {
        TextView passwordText = (TextView) findViewById(R.id.userPassword);
        passwordText.setError(errorMessage);
    }


}