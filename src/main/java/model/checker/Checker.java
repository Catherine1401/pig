package model.checker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

public class Checker {
    // check username
    public static void checkUsername(String username) throws InputMismatchException {
        if (username.equals(""))
            throw new InputMismatchException("Username is empty. Please enter a username!");
    }

    // check password
    public static void checkPassword(String password) throws InputMismatchException {
        if (password.equals(""))
            throw new InputMismatchException("Password is empty. Please enter a password!");
    }

    // check id isempty
    public static void checkIdEmpty(String id) throws InputMismatchException {
        if (id.equals(""))
            throw new InputMismatchException("ID cannot be empty!");
    }

    // check id invalid
    public static void checkId(String id) throws InputMismatchException {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException nfe) {
            throw new InputMismatchException("ID must be a valid integer!");
        }
    }

    // check type empty
    public static void checktypeEmpty(String pigBread) throws InputMismatchException {
        if (pigBread.equals(""))
            throw new InputMismatchException("Pig bread cannot be empty!");
    }

    // check quantity isempty
    public static void checkQuantityEmpty(String quantity) throws InputMismatchException {
        if (quantity.equals(""))
            throw new InputMismatchException("Quantity cannot be empty!");
    }

    // check quantity invalid
    public static void checkQuantity(String quantity) throws InputMismatchException {
        try {
            int clm = Integer.parseInt(quantity);
            if (clm <= 0 || clm > 10000)
                throw new InputMismatchException(
                        "The quantity must be greater than 0 and less than or equal to 10.000!");
        } catch (NumberFormatException nfe) {
            throw new InputMismatchException("Quantity must be a valid integer!");
        }
    }

    // check price isempty
    public static void checkPriceEmpty(String price) throws InputMismatchException {
        if (price.equals(""))
            throw new InputMismatchException("Price cannot be empty!");
    }

    // check price invalid
    public static void checkPrice(String price) throws InputMismatchException {
        try {
            Long clm = Long.parseLong(price);
            if (clm <= 100000 || clm > 5000000)
                throw new InputMismatchException(
                        "The price must be greater than 100.000 and less than or equal to 5.000.000!");
        } catch (NumberFormatException nfe) {
            throw new InputMismatchException("Price must be a valid integer!");
        }
    }

    // check age isempty
    public static void checkAgeEmpty(String age) throws InputMismatchException {
        if (age.equals(""))
            throw new InputMismatchException("Age cannot be empty!");
    }

    // check age invalid
    public static void checkAge(String age) throws InputMismatchException {
        try {
            int clm = Integer.parseInt(age);
            if (clm <= 0 || clm > 30)
                throw new InputMismatchException("The age must be greater than 0 and less than or equal to 30!");
        } catch (NumberFormatException nfe) {
            throw new InputMismatchException("Age must be a valid integer!");
        }
    }

    // check date isempty
    public static void checkDateEmpty(String date) throws InputMismatchException {
        if (date.equals(""))
            throw new InputMismatchException("Date cannot be empty!");
    }

    // check date
    public static void checkDate(String date) throws InputMismatchException {
        DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate lDate = LocalDate.parse(date, dFormatter);
            if (lDate.isAfter(LocalDate.now()))
                throw new InputMismatchException("The date must be earlier than the current date!");
        } catch (DateTimeParseException dtpe) {
            throw new InputMismatchException("Invalid date format. Date must be in dd/MM/yyyy format");
        }
    }

    // check vaccine isempty
    public static void checkVaccineEmpty(String vaccine) throws InputMismatchException {
        if (vaccine.equals(""))
            throw new InputMismatchException("Vaccination status cannot be empty!");
    }

    public static void check(String username, String password) throws InputMismatchException {
        checkUsername(username);
        checkPassword(password);
    }

    public static void checkEmpty(String id, String pigBread, String quantity, String price, String age, String date,
            String vaccine) throws InputMismatchException {
        checkIdEmpty(id);
        checktypeEmpty(pigBread);
        checkQuantityEmpty(quantity);
        checkPriceEmpty(price);
        checkAgeEmpty(age);
        checkDateEmpty(date);
        checkVaccineEmpty(vaccine);
    }

    public static void check(String id, String quantity, String price, String age, String date)
            throws InputMismatchException {
        checkId(id);
        checkQuantity(quantity);
        checkPrice(price);
        checkAge(age);
        checkDate(date);
    }
}
