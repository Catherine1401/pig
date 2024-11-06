package model.manager;

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
import java.time.format.DateTimeParseException;

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
}
