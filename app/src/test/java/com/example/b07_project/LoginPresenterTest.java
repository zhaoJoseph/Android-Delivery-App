package com.example.b07_project;

import com.example.b07_project.Model.LoginModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LoginPresenterTest {
    @Mock
    LoginModel model;
    @Mock
    Contract.View view;
    @InjectMocks
    LoginPresenter loginPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAttemptLogin() throws Exception {
        when(model.attemptLogin(anyString(), anyString())).thenReturn("attemptLoginResponse");
        when(view.getEmail()).thenReturn("getEmailResponse");
        when(view.getPassword()).thenReturn("getPasswordResponse");

        loginPresenter.attemptLogin();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme