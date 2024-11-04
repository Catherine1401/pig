package model.checker;

import java.util.InputMismatchException;

public class Checker {
    public static void checker(String username, String password) throws InputMismatchException {
        if (username.equals(""))
            throw new InputMismatchException("Username is empty. Please enter a username!");
        if (password.equals(""))
            throw new InputMismatchException("Password is empty. Please enter a password!");
    }
}
