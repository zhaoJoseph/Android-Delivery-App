package com.example.b07_project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import encryption.MD5Encryption;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);

    }
    public void launch_page(boolean IsCustomer){
        Intent intent;
        if(IsCustomer){
            intent = new Intent(this, StoreListActivity.class);
        }
        else{
            intent = new Intent(this, StoreActivity.class);
        }
        startActivity(intent);
    }
    /*
    uncomment this once we have a signout button else impossible to signout
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            boolean IsCustomer=true; // access user data
            launch_page(IsCustomer);
        }
    }*/

    public void loginAccount(View view){
        TextView emailText = (TextView) findViewById(R.id.userEmailAddress);
        TextView passwordText = (TextView) findViewById(R.id.userPassword);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        MD5Encryption encrypt = new MD5Encryption();
        String encryptedPassword = encrypt.encrypt(password);

        //TODO check if encryptedPassword matches with data in the firebase, if yes go into the next page
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            launch_page(true);//hard coded for now because i need to setup the realtime database
                        } else {
                            // If sign in fails, display a message to the user.

                            try {
                                Toast.makeText(MainActivity.this, "Authentication failed." + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }catch (NullPointerException e){
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
        /*
        emailText.setText("");
        passwordText.setText("");
        annoying to retype password and email
        */
    }

    public void signupAccount(View view){

        Intent intent = new Intent(this, signUpActivity.class);

        startActivity(intent);
    }
}