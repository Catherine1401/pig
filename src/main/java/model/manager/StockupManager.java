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
        } else
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
                            new Object[] { row[0], row[1], row[2], dFormat.format(Long.parseLong(row[3])), row[4],
                                    row[5], row[6] });
                }
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        } catch (IOException ioe) {
            ioe.getStackTrace();
        }
    }

    // filter and
    public DefaultTableModel filterAnd(String idF, String idT, String type, String quantityF, String quantityT,
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

        DefaultTableModel tempModel = new DefaultTableModel();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            // parse
            try {
                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);

                int id = Integer.parseInt(row.get(0).toString());
                int quantity = Integer.parseInt(row.get(2).toString());
                String str = row.get(3).toString().replace(".", "");
                long price = Long.parseLong(str);
                int age = Integer.parseInt(row.get(4).toString());
                DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);

                if (id >= idf && id <= idt && (type.equals("") || row.get(1).equals(type))
                        && quantity >= quantityf && quantity <= quantityt
                        && price >= pricef && price <= pricet
                        && age >= agef && age <= aget
                        && (date.isAfter(datef) || date.isEqual(datef))
                        && (date.isBefore(datet) || date.isEqual(datet))
                        && (vaccine.equals("") || row.get(6).equals(vaccine)))
                    tempModel.addRow(
                            new Object[] { row.get(0), row.get(1), row.get(2), dFormat.format(price), row.get(4),
                                    row.get(5), row.get(6) });
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

        }
        return tempModel;
    }

    // filter or
    public DefaultTableModel filterOr(String idF, String idT, String type, String quantityF, String quantityT,
            String priceF, String priceT, String ageF, String ageT, String dateF, String dateT, String vaccine)
            throws InputMismatchException {

        // parse
        int idf = parse(idF, Integer.MAX_VALUE);
        int idt = parse(idT, Integer.MIN_VALUE);
        int quantityf = parse(quantityF, Integer.MAX_VALUE);
        int quantityt = parse(quantityT, Integer.MIN_VALUE);
        Long pricef = parse(priceF, Long.MAX_VALUE);
        Long pricet = parse(priceT, Long.MIN_VALUE);
        int agef = parse(ageF, Integer.MAX_VALUE);
        int aget = parse(ageT, Integer.MIN_VALUE);
        LocalDate datef = parse(dateF, LocalDate.MAX);
        LocalDate datet = parse(dateT, LocalDate.MIN);

        DefaultTableModel tempModel = new DefaultTableModel();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            // parse
            try {
                DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
                dSymbols.setGroupingSeparator('.');
                DecimalFormat dFormat = new DecimalFormat("#,###");
                dFormat.setDecimalFormatSymbols(dSymbols);

                int id = Integer.parseInt(row.get(0).toString());
                int quantity = Integer.parseInt(row.get(2).toString());
                String str = row.get(3).toString().replace(".", "");
                long price = Long.parseLong(str);
                int age = Integer.parseInt(row.get(4).toString());
                DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);

                if ((id >= idf && id <= idt)
                        || (!type.equals("") && row.get(1).equals(type))
                        || (quantity >= quantityf && quantity <= quantityt)
                        || (price >= pricef && price <= pricet)
                        || (age >= agef && age <= aget)
                        || ((date.isAfter(datef) || date.isEqual(datef))
                                && (date.isBefore(datet) || date.isEqual(datet)))
                        || (!vaccine.equals("") && row.get(6).equals(vaccine)))
                    tempModel.addRow(
                            new Object[] { row.get(0), row.get(1), row.get(2), dFormat.format(price), row.get(4),
                                    row.get(5), row.get(6) });
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }

        }
        return tempModel;
    }

    // sort id ascending
    public DefaultTableModel sortIdAs() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Integer.compare(s1.getId(), s2.getId());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort id descending
    public DefaultTableModel sortIdDe() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Integer.compare(s2.getId(), s1.getId());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort quantity ascending
    public DefaultTableModel sortQuantityAs() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Integer.compare(s1.getQuantity(), s2.getQuantity());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort quantity descending
    public DefaultTableModel sortQuantityDe() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Integer.compare(s2.getQuantity(), s1.getQuantity());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort price ascending
    public DefaultTableModel sortPriceAs() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Long.compare(s1.getPrice(), s2.getPrice());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort price descending
    public DefaultTableModel sortPriceDe() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Long.compare(s2.getPrice(), s1.getPrice());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort age ascending
    public DefaultTableModel sortAgeAs() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Long.compare(s1.getAge(), s2.getAge());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort age descending
    public DefaultTableModel sortAgeDe() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return Long.compare(s2.getAge(), s1.getAge());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sort date ascending
    public DefaultTableModel sortDateAs() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return s1.getDate().compareTo(s2.getDate());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // sỏt date descending
    public DefaultTableModel sortDateDe() {
        DefaultTableModel tempModel = new DefaultTableModel();
        list = new ArrayList<>();
        tempModel.setColumnIdentifiers(
                new String[] { "ID", "Giống Lợn", "Số Lượng", "Giá", "Ngày Tuổi", "Ngày Nhập", "Tiêm Chủng" });

        DecimalFormatSymbols dSymbols = new DecimalFormatSymbols();
        dSymbols.setGroupingSeparator('.');
        DecimalFormat dFormat = new DecimalFormat("#,###");
        dFormat.setDecimalFormatSymbols(dSymbols);

        Vector<Vector> rows = dModel.getDataVector();
        for (Vector<String> row : rows) {
            int id = Integer.parseInt(row.get(0).toString());
            String pigBread = row.get(1);
            int quantity = Integer.parseInt(row.get(2).toString());
            String str = row.get(3).toString().replace(".", "");
            long price = Long.parseLong(str);
            int age = Integer.parseInt(row.get(4).toString());
            DateTimeFormatter dFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(row.get(5).toString(), dFormatter);
            boolean vaccine = Boolean.parseBoolean(row.get(6));
            list.add(new StockupOrder(id, pigBread, quantity, price, age, date, vaccine));
        }

        Collections.sort(list, new Comparator<StockupOrder>() {
            @Override
            public int compare(StockupOrder s1, StockupOrder s2) {
                return s2.getDate().compareTo(s1.getDate());
            }
        });

        for (StockupOrder s : list) {
            tempModel.addRow(new Object[] { s.getId(), s.getType(), s.getQuantity(), dFormat.format(s.getPrice()),
                    s.getAge(), s.getDate(), s.isVaccineStatus() });
        }
        return tempModel;
    }

    // add
    public void add(String type, String quantity, String price, String age, String date, String vaccine)
            throws InputMismatchException {
        Checker.checkEmpty("0", type, quantity, price, age, date, vaccine);
        Checker.check("0", quantity, price.replace(".", ""), age, date);

        input();
        dModel.addRow(new Object[] { dModel.getRowCount() + 1, type, quantity, price, age, date, vaccine });
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File("src/resources/stockup.txt"), true))) {
            String data = (dModel.getRowCount()) + ";" + type + ";" + quantity + ";" + price.replace(".", "") + ";" + age + ";" + date + ";" + vaccine + "\n" ;
            bWriter.write(data);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // delete
    public void delete(String id) throws InputMismatchException {
        Checker.checkIdEmpty(id);
        Checker.checkId(id);
        
        List<String> lines = new ArrayList<>();
        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/stockup.txt")))) {
            String line;
            while((line = bReader.readLine()) != null) {
                String[] row = line.split(";");
                if (!row[0].equals(id))
                    lines.add(row[1] + ";" + row[2] + ";" + row[3] + ";" + row[4] + ";" + row[5] + ";" + row[6]);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File("src/resources/stockup.txt")))) {
            int i = 1;
            for (String l : lines)
                bWriter.write(i++ + ";" + l + "\n");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        input();
    }

    // update
    public void update(String id, String type, String quantity, String price, String age, String date, String vaccine)
        throws InputMismatchException {
        Checker.checkEmpty(id, type, quantity, price, age, date, vaccine);
        Checker.check(id, quantity, price.replace(".", ""), age, date);

        List<String> lines = new ArrayList<>();
        try (BufferedReader bReader = new BufferedReader(new FileReader(new File("src/resources/stockup.txt")))) {
            String line;
            while((line = bReader.readLine()) != null) {
                String[] row = line.split(";");
                if (row[0].equals(id)) {
                    row[1] = type;
                    row[2] = quantity;
                    row[3] = price.replace(".", "");
                    row[4] = age;
                    row[5] = date;
                    row[6] = vaccine;
                }
                lines.add(row[1] + ";" + row[2] + ";" + row[3] + ";" + row[4] + ";" + row[5] + ";" + row[6]);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File("src/resources/stockup.txt")))) {
            int i = 1;
            for (String l : lines)
                bWriter.write(i++ + ";" + l + "\n");
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
