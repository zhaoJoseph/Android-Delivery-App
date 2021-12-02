package com.example.b07_project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.b07_project.Model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_main);

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
    /*
    public void launch_page(){
        Intent intent;
        IsCustomer = false;
        String userID = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData u = snapshot.getValue(UserData.class);
                if(u != null){
                    intent = null;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error getting User Data",
                        Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                return;
            }
        });
        Toast.makeText(MainActivity.this, IsCustomer.toString(),
                Toast.LENGTH_SHORT).show();
        if(IsCustomer){
            intent = new Intent(this, OP2Activity.class);
        }
        else{
            intent = new Intent(this, OP1Activity.class);
        }
        startActivity(intent);
        finish();

    }

*/

    public void loginAccount(View view){
        TextView emailText = (TextView) findViewById(R.id.userEmailAddress);
        TextView passwordText = (TextView) findViewById(R.id.userPassword);

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        //TODO check if encryptedPassword matches with data in the firebase, if yes go into the next page
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    UserData u = snapshot.getValue(UserData.class);
                                    if(u != null){
                                        launch_page(u.getIsCustomer());
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(MainActivity.this, "Error getting User Data",
                                            Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                    return;
                                }
                            });
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
    }
/*
    @Override
    public void onStart() {
    super.onStart();
    // Check if user is signed in (non-null) and update UI accordingly.
    FirebaseUser currentUser = mAuth.getCurrentUser();

    if(currentUser != null){
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData u = snapshot.getValue(UserData.class);
                if(u != null){
                    launch_page(u.getIsCustomer());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error getting User Data",
                        Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                return;
            }
        });
    }
    }*/
    public void signupAccount(View view){

        Intent intent = new Intent(this, signUpActivity.class);

        startActivity(intent);
    }
}