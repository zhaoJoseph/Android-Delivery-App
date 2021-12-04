package com.example.b07_project;

import com.example.b07_project.Model.LoginModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginModel model;
    @Mock
    MainActivity view;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAttemptLogin() throws Exception {

        String Email = "owner@example.com";
        String password = "123456";

        when(view.getEmail()).thenReturn(Email);
        when(view.getPassword()).thenReturn(password);

        LoginPresenter presenter = new LoginPresenter(model, view);
        doNothing().when(model).attemptLogin(Email, password, presenter);

        presenter.attemptLogin();

        verify(model, times(1)).attemptLogin(Email, password, presenter);

    }

    @Test
    public void test_launch_page_or_display_error() throws Exception{

        String errorMessage = "Error Message";
        String owner = "owner";
        String customer = "customer";
        doNothing().when(view).launch_page_owner();
        doNothing().when(view).launch_page_customer();
        doNothing().when(view).displayError(errorMessage);

        LoginPresenter presenter = new LoginPresenter(model, view);
        presenter.launch_page_or_display_error(owner);
        presenter.launch_page_or_display_error(customer);
        presenter.launch_page_or_display_error(errorMessage);


        verify(view, times(1)).launch_page_owner();
        verify(view, times(1)).launch_page_customer();
        verify(view, times(1)).displayError(errorMessage);
    }

}
