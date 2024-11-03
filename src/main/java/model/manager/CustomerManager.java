package model.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

public class CustomerManager {
    private File info;;

    public CustomerManager() {
    }

    public void checkInfo(String username, String password) throws InputMismatchException {
        info = new File("src/resources/login.txt");
        try (BufferedReader bReader = new BufferedReader(new FileReader(info))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data[0].equals(username) && data[1].equals(password))
                    return;
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
        throw new InputMismatchException("Username or password is incorrect!");
    }

}
