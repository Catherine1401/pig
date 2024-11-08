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
    private TableRowSorter<DefaultTableModel> tSorter;

    public StockupManager() {
        input();
    }

    public DefaultTableModel getdModel() {
        return dModel;
    }

    public List<StockupOrder> getList() {
        return list;
    }

    public TableRowSorter<DefaultTableModel> gettSorter() {
        return tSorter;
    }

    private void input() {
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
        tSorter = new TableRowSorter<>(dModel);
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

    public void search(String query) throws InputMismatchException {
        if (query.equals(""))
            throw new InputMismatchException("Please enter a valid search keyword!");
        tSorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
    }

    public void filterAnd(String idF, String idT, String type, String quantityF, String quantityT,
            String priceF, String priceT, String ageF, String ageT, String dateF, String dateT, String vaccine)
            throws InputMismatchException {    
        Checker.check(idT, quantityT, priceT, ageT, dateT);
        Checker.check(idF, quantityF, priceF, ageF, dateF);

        // parse
        int idf = Integer.valueOf(idF);
        int idt = Integer.parseInt(idT);
        int quantityf = Integer.parseInt(quantityF);
        int quantityt = Integer.parseInt(quantityT);
        Long pricef = Long.parseLong(priceF);
        Long pricet = Long.parseLong(priceT);
        int agef = Integer.parseInt(ageF);
        int aget = Integer.parseInt(ageT);
        DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate datef = LocalDate.parse(dateF, dFormatter);
        LocalDate datet = LocalDate.parse(dateT, dFormatter);

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
                LocalDate date = LocalDate.parse(row[5], dFormatter);

                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);

                if (id >= idf && id <= idt && row[1].equals(type) 
                && quantity >= quantityf && quantity <= quantityt 
                && price >= pricef && price <= pricet 
                && age >= agef && age <= aget 
                && (date.isBefore(datef) || date.isEqual(datef)) 
                && (date.isAfter(datet) || date.isEqual(datet)) 
                && row[6].equals(vaccine))
                dModel.addRow(new Object[] {row[0], row[1], row[2], dFormat.format(price), row[4], row[5], row[6]});
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }
}
