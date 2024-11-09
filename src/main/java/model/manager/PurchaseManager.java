package model.manager;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.checker.Checker;
import model.order.StockupOrder;

public class PurchaseManager {
    private List<StockupOrder> list;
    private DefaultTableModel dModel;

    public PurchaseManager() {
        input();
    }

    public DefaultTableModel getdModel() {
        return dModel;
    }

    public List<StockupOrder> getList() {
        return list;
    }

    public void input() {
        dModel = new DefaultTableModel();
        dModel.setColumnIdentifiers(
                new String[] { "Giống Lợn", "Số Lượng", "Giá (/kg)" });

        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/purchase.txt")))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] row = line.split(";");
                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);
                dModel.addRow(new Object[] { row[0], row[1], dFormat.format(Long.valueOf(row[2])) });
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        } catch (DateTimeException dte) {
            dte.getStackTrace();
        }
    }

    public void initTable(JTable jTable) {
        // set column width
        int[] columnWidth = { 140, 60, 110 };
        for (int i = 0; i < columnWidth.length; i++)
            jTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);

        // set header
        jTable.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 12));
        jTable.setFont(new Font("Roboto", Font.PLAIN, 14));

    }

    // buy
    public void buy(String type, String quantity, String price) throws InputMismatchException {
        Checker.checkEmpty("0", type, quantity, price, "1", "01/01/2021", "true");
        Checker.check("0", quantity, price.replace(".", ""), "1", "01/01/2021");

        List<String> list = new ArrayList<>();

        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/purchase.txt")))) {
            String line;
            while((line = bReader.readLine()) != null) {
                String[] row = line.split(";");
                if (row[0].equals(type)) {
                    if (Integer.parseInt(quantity) > Integer.parseInt(row[1]))
                        throw new InputMismatchException("Quantity must be less than available stock!");
                    row[1] = (Integer.parseInt(row[1]) - Integer.parseInt(quantity)) + "";
                }
                list.add(row[0] + ";" + row[1] + ";" + row[2]);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File("src/resources/purchase.txt")))) {
            for (String row : list) {
                bWriter.write(row + "\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        input();
    }

    private int parse(String input, int defaultValue) {
        try {
            return input.isEmpty() ? defaultValue : Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            throw new InputMismatchException("ID must be a valid integer!");
        }
    }

    private long parse(String input, long defaultValue) {
        try {
            return input.isEmpty() ? defaultValue : Long.parseLong(input);
        } catch (NumberFormatException nfe) {
            throw new InputMismatchException("ID must be a valid integer!");
        }
    }

    private LocalDate parse(String input, LocalDate defaultValue) {
        try {
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return input.isEmpty() ? defaultValue : LocalDate.parse(input, dFormatter);
        } catch (DateTimeParseException dtpe) {
            throw new InputMismatchException("Invalid date format. Date must be in dd/MM/yyyy format");
        }
    }
}
