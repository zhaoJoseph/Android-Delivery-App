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
        public String attemptLogin(String email, String password);
    }

    public interface presenter{
        public void attemptLogin();
    }
}
