package com.example.b07_project;

import android.view.View;

import com.example.b07_project.Model.LoginModel;

public class LoginPresenter implements Contract.presenter{

    private LoginModel model;
    private Contract.View view;

    public LoginPresenter(LoginModel model, Contract.View view){
        this.model = model;
        this.view = view;
    }


    @Override
    public void attemptLogin(){



        String email = view.getEmail();
        String password = view.getPassword();


        model.attemptLogin(email, password,view);




//        if (loggingIn.equals("customer")){
//            view.launch_page_customer();
//        }
//        else if (loggingIn.equals("owner")){
//            view.launch_page_owner();
//        }
//
//        else{
//            view.displayError(loggingIn);
//
//        }

    }
}
