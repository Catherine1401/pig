package model.manager;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StockupManager {
    private DefaultTableModel dModel;

    public StockupManager() {
        input();
    }

    public DefaultTableModel getdModel() {
        return dModel;
    }

    private void input() {
        dModel = new DefaultTableModel();
        dModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/stockup.txt")))) {
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] row = line.split(";");
                DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);
                dModel.addRow(new Object[] { Integer.valueOf(row[0]), row[1], Integer.valueOf(row[2]),
                        dFormat.format(Long.valueOf(row[3])), Integer.valueOf(row[4]), LocalDate.parse(row[5], dFormatter),
                        Boolean.valueOf(row[6]) });
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        } catch (DateTimeException dte) {
            dte.getStackTrace();
        }
    }

    public static void initTable(JTable jTable) {
        // set column width
        int[] columnWidth = {30, 140, 60, 110, 40, 80, 50};
        for (int i = 0; i < columnWidth.length; i++)
            jTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth[i]);

        // set header
        jTable.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 13));
        jTable.setFont(new Font("Roboto", Font.PLAIN, 14));
    }
}
