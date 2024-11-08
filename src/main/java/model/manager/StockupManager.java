package model.manager;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import model.checker.Checker;
import model.order.StockupOrder;

public class StockupManager {
    private List<StockupOrder> list;
    private DefaultTableModel dModel;

    public StockupManager() {
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
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/stockup.txt")))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] row = line.split(";");
                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);
                dModel.addRow(new Object[] { row[0], row[1], row[2], dFormat.format(Long.valueOf(row[3])), row[4],
                        row[5], row[6] });
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
        int[] columnWidth = { 30, 140, 60, 110, 40, 80, 50 };
        for (int i = 0; i < columnWidth.length; i++)
            jTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);

        // set header
        jTable.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 12));
        jTable.setFont(new Font("Roboto", Font.PLAIN, 14));

    }

    // search
    public void search(String query) throws InputMismatchException {
        if (query.equals("")) {
            input();
            throw new InputMismatchException("Please enter text in the search box!");
        }
        else
            dModel = new DefaultTableModel();
            dModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });
                try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/stockup.txt")))) {
                    String line;
                    DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                    dSymbols.setGroupingSeparator('.');
                    DecimalFormat dFormat = new DecimalFormat("#,###");
                    dFormat.setDecimalFormatSymbols(dSymbols);
                    while ((line = bReader.readLine()) != null) {
                        if (line.toLowerCase().contains(query.toLowerCase())) {
                            String[] row = line.split(";");
                            dModel.addRow(
                                new Object[] { row[0], row[1], row[2], dFormat.format(Long.parseLong(row[3])), row[4], row[5], row[6] });
                        }
                        
                    }
                } catch (FileNotFoundException fnfe) {
                    fnfe.getStackTrace();
                } catch (IOException ioe) {
                    ioe.getStackTrace();
                }
    }

    // filter and
    public void filterAnd(String idF, String idT, String type, String quantityF, String quantityT,
            String priceF, String priceT, String ageF, String ageT, String dateF, String dateT, String vaccine)
            throws InputMismatchException {

        // parse
        int idf = parse(idF, Integer.MIN_VALUE);
        int idt = parse(idT, Integer.MAX_VALUE);
        int quantityf = parse(quantityF, Integer.MIN_VALUE);
        int quantityt = parse(quantityT, Integer.MAX_VALUE);
        Long pricef = parse(priceF, Long.MIN_VALUE);
        Long pricet = parse(priceT, Long.MAX_VALUE);
        int agef = parse(ageF, Integer.MIN_VALUE);
        int aget = parse(ageT, Integer.MAX_VALUE);
        LocalDate datef = parse(dateF, LocalDate.MIN);
        LocalDate datet = parse(dateT, LocalDate.MAX);

        dModel = new DefaultTableModel();
        dModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });
        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/stockup.txt")))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                // parse
                String[] row = line.split(";");
                int id = Integer.parseInt(row[0]);
                int quantity = Integer.parseInt(row[2]);
                long price = Long.parseLong(row[3]);
                int age = Integer.parseInt(row[4]);
                DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(row[5], dFormatter);

                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);

                if (id >= idf && id <= idt && (type.equals("") || row[1].equals(type))
                        && quantity >= quantityf && quantity <= quantityt
                        && price >= pricef && price <= pricet
                        && age >= agef && age <= aget
                        && (date.isAfter(datef) || date.isEqual(datef))
                        && (date.isBefore(datet) || date.isEqual(datet))
                        && (vaccine.equals("") || row[6].equals(vaccine)))
                    dModel.addRow(
                            new Object[] { row[0], row[1], row[2], dFormat.format(price), row[4], row[5], row[6] });
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }

    // filter or
    public void filterOr(String idF, String idT, String type, String quantityF, String quantityT,
            String priceF, String priceT, String ageF, String ageT, String dateF, String dateT, String vaccine)
            throws InputMismatchException {

        // parse
        int idf = parse(idF, Integer.MIN_VALUE);
        int idt = parse(idT, Integer.MAX_VALUE);
        int quantityf = parse(quantityF, Integer.MIN_VALUE);
        int quantityt = parse(quantityT, Integer.MAX_VALUE);
        Long pricef = parse(priceF, Long.MIN_VALUE);
        Long pricet = parse(priceT, Long.MAX_VALUE);
        int agef = parse(ageF, Integer.MIN_VALUE);
        int aget = parse(ageT, Integer.MAX_VALUE);
        LocalDate datef = parse(dateF, LocalDate.MIN);
        LocalDate datet = parse(dateT, LocalDate.MAX);

        dModel = new DefaultTableModel();
        dModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });
        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/stockup.txt")))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                // parse
                String[] row = line.split(";");
                int id = Integer.parseInt(row[0]);
                int quantity = Integer.parseInt(row[2]);
                long price = Long.parseLong(row[3]);
                int age = Integer.parseInt(row[4]);
                DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(row[5], dFormatter);

                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);

                if (id >= idf && id <= idt || (type.equals("") || row[1].equals(type))
                        || quantity >= quantityf && quantity <= quantityt
                        || price >= pricef && price <= pricet
                        || age >= agef && age <= aget
                        || (date.isAfter(datef) || date.isEqual(datef))
                        || (date.isBefore(datet) || date.isEqual(datet))
                        || (vaccine.equals("") || row[6].equals(vaccine)))
                    dModel.addRow(
                            new Object[] { row[0], row[1], row[2], dFormat.format(price), row[4], row[5], row[6] });
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
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
