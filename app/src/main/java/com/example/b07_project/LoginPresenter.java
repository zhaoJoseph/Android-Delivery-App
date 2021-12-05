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

        //firebase crashes the app if the inputs are null
        if(email.length() == 0){
            view.displayEmailError("email can not be empty");
            return;
        }

        //firebase crashes the app if the inputs are null
        if (password.length() == 0){
            view.displayPasswordError("password can not be empty");
            return;
        }


        model.attemptLogin(email, password, this);

    }

    @Override
    public void launch_page_or_display_error(String output) {

        if (output.equals("customer")){
            view.launch_page_customer();
        }
        else if (output.equals("owner")){
            view.launch_page_owner();
        }

        else{
            view.displayError(output);

        }


    }
}
