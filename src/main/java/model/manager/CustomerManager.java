package model.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

import model.checker.Checker;

public class CustomerManager {
    public CustomerManager() {
    }

    public boolean checkLogin(String username, String password) throws InputMismatchException {
        Checker.checker(username, password);
        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/login.txt")))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data[0].equals(username) && data[1].equals(password))
                    return true;
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
        return false;
    }

    public boolean checkSignup(String username, String password) {
        Checker.checker(username, password);
        File info = new File("src/resources/login.txt");
        Map<String, String> list = new HashMap<>();
        try (BufferedReader bReader = new BufferedReader(new FileReader(info))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] data = line.split(";");
                if (data[0].equals(username))
                    return false;
                list.put(data[0], data[1]);
            }
            list.put(username, password);
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(info))) {
            for (String key : list.keySet())
                bWriter.write(key + ";" + list.get(key) + "\n");
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
        
        return true;
    }

}
