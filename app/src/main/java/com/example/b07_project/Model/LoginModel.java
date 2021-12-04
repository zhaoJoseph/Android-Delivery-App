package com.example.b07_project.Model;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.b07_project.Contract;
import com.example.b07_project.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel implements Contract.model{

    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;

    public LoginModel(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void attemptLogin(String email, String password, Contract.presenter pres){


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    UserData u = snapshot.getValue(UserData.class);
                                    if(u != null){

                                        if (u.getIsCustomer()){
                                            pres.launch_page_or_display_error("customer");
                                        }
                                        else{
                                            pres.launch_page_or_display_error("owner");
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    pres.launch_page_or_display_error("Error getting User Data");
                                    mAuth.signOut();
                                    return;
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.

                            try {

                                String text = "Authentication failed." + task.getException().getMessage();
                                pres.launch_page_or_display_error(text);
                            }catch (NullPointerException e){

                                pres.launch_page_or_display_error("Authentication failed");

                            }

                        }
                    }
                });




    }



}
