package com.example.b07_project;

public interface Contract {

    public interface View{
        public String getEmail();
        public String getPassword();
        public void displayError(String errorMessage);
        public void launch_page_customer();
        public void launch_page_owner();
    }

    public interface model{
        public void attemptLogin(String email, String password, Contract.presenter pres);
    }

    public interface presenter{
        public void attemptLogin();
        public void launch_page_or_display_error(String output);
    }
}
