package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.LoginView;

public class LoginController implements ActionListener {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String clm = e.getActionCommand();
        switch (clm) {
            case "Login" -> view.login();
            case "Sign up" -> view.signup();
            case "Create" -> view.create();
            case "Cancel" -> view.cancel();
        }
    }

}
