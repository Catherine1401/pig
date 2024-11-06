package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import controller.AdminController;
import model.manager.StockupManager;

public class AdminView extends JFrame {
    private JPanel mainView;
    private CardLayout cardLayout;

    private JMenuItem jItemStockup;
    private JMenuItem jItemCustomer;
    private JTextField jFieldSearchStockup;
    private JTextField jFieldIdFromStockup;
    private JTextField jFieldIdToStockup;
    private JComboBox<String> jBoxTypeFromStockup;
    private JComboBox<String> jBoxTypeToStockup;
    private JTextField jFieldQuantityFromStockup;
    private JTextField jFieldQuantityToStockup;
    private JTextField jFieldPriceFromStockup;
    private JTextField jFieldPriceToStockup;
    private JTextField jFieldAgeFromStockup;
    private JTextField jFieldAgeToStockup;
    private JTextField jFieldDateFromStockup;
    private JTextField jFieldDateToStockup;
    private JComboBox<String> jBoxVaccineStockup;
    private JComboBox<String> jBoxOptionStock;
    private JTable jTable;

    private final StockupManager sManager = new StockupManager();

    public AdminView() {
        init();
        setVisible(true);
    }

    public void init() {
        setTitle("ADMIN");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainView = new JPanel(cardLayout);

        mainView.add(stockupView(), "stockup");
        mainView.add(customerView(), "customer");
        add(mainView);

        cardLayout.first(mainView);

        // --- set menu ---
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenuFile = new JMenu("File");
        Font fontMenu = new Font("Roboto", Font.BOLD, 13);
        jMenuFile.setFont(fontMenu);
        jMenuFile.setMnemonic(KeyEvent.VK_F);

        JMenu jMenuView = new JMenu("View");
        jMenuView.setFont(fontMenu);
        jMenuView.setMnemonic(KeyEvent.VK_V);

        JMenu jMenuHelp = new JMenu("Help");
        jMenuHelp.setFont(fontMenu);
        jMenuHelp.setMnemonic(KeyEvent.VK_H);

        JMenuItem jItemOpen = new JMenuItem("Open");
        jItemOpen.setFont(fontMenu);
        jItemOpen.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));

        JMenuItem jItemClose = new JMenuItem("Close");
        jItemClose.setFont(fontMenu);
        jItemClose.setAccelerator(KeyStroke.getKeyStroke("ctrl shift O"));

        JMenuItem jItemExit = new JMenuItem("Exit");
        jItemExit.setFont(fontMenu);
        jItemExit.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
        ActionListener ac = new AdminController(this);
        jItemExit.addActionListener(ac);

        jItemStockup = new JMenuItem("Stock Up");
        jItemStockup.setFont(fontMenu);
        jItemStockup.setAccelerator(KeyStroke.getKeyStroke("alt S"));
        jItemStockup.addActionListener(ac);

        jItemCustomer = new JMenuItem("Customer");
        jItemCustomer.setFont(fontMenu);
        jItemCustomer.setAccelerator(KeyStroke.getKeyStroke("alt C"));
        jItemCustomer.setEnabled(false);
        jItemCustomer.addActionListener(ac);

        JMenuItem jItemDocument = new JMenuItem("Document");
        jItemDocument.setFont(fontMenu);
        jItemDocument.setAccelerator(KeyStroke.getKeyStroke("alt D"));

        JMenuItem jItemAbout = new JMenuItem("About");
        jItemAbout.setFont(fontMenu);
        jItemAbout.setAccelerator(KeyStroke.getKeyStroke("alt A"));

        jMenuFile.add(jItemOpen);
        jMenuFile.add(jItemClose);
        jMenuFile.addSeparator();
        jMenuFile.add(jItemExit);
        jMenuView.add(jItemStockup);
        jMenuView.add(jItemCustomer);
        jMenuHelp.add(jItemDocument);
        jMenuHelp.add(jItemAbout);

        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuView);
        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);
    }

    public JPanel stockupView() {
        JPanel stockupView = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Font fontPlain = new Font("Roboto", Font.PLAIN, 15);
        Font fontBold = new Font("Roboto", Font.BOLD, 15);

        // --- set search tool ---
        JPanel jPanelSearch = new JPanel(new GridBagLayout());

        // ++ add search field ++
        jFieldSearchStockup = new JTextField();
        jFieldSearchStockup.setFont(fontPlain);
        jFieldSearchStockup.setPreferredSize(new Dimension(750, 30));
        jFieldSearchStockup.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        Insets insets = new Insets(0, 0, 0, 15);
        gbc.insets = insets;
        jPanelSearch.add(jFieldSearchStockup, gbc);

        // ++ add search button ++
        JButton jButtonSearch = new JButton("Search");
        jButtonSearch.setFont(fontBold);
        gbc.gridx = 1;
        insets.set(0, 0, 0, 0);
        gbc.insets = insets;
        jPanelSearch.add(jButtonSearch);

        // --- add filter tool ---
        JPanel jPanelFilter = new JPanel(new GridBagLayout());

        // ++ add label from ++
        JLabel jLabelFrom = new JLabel("From");
        jLabelFrom.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 0;
        insets.set(0, 0, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;
        jPanelFilter.add(jLabelFrom, gbc);

        // ++ add label to ++
        JLabel jLabelTo = new JLabel("To");
        jLabelTo.setFont(fontBold);
        gbc.gridy = 1;
        insets.set(0, 0, 0, 15);
        jPanelFilter.add(jLabelTo, gbc);

        // ++ add id field ++
        jFieldIdFromStockup = new JTextField();
        jFieldIdFromStockup.setFont(fontPlain);
        jFieldIdFromStockup.setPreferredSize(new Dimension(55, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        jPanelFilter.add(jFieldIdFromStockup, gbc);

        jFieldIdToStockup = new JTextField();
        jFieldIdToStockup.setFont(fontPlain);
        jFieldIdToStockup.setPreferredSize(new Dimension(55, 30));
        gbc.gridy = 1;
        jPanelFilter.add(jFieldIdToStockup, gbc);

        // ++ add type field ++
        jBoxTypeFromStockup = new JComboBox<>(new String[] { "Móng Cái", "Ỉ", "Mán", "Sóc", "Cỏ", "Lũng Pù", "Vân Pa",
                "Mường Khương", "Mẹo", "Táp Ná", "Yorkshire", "Landrace", "Pietrain", "Hampshire", "Berkshire",
                "Cornwall", "Ba Xuyên", "Thuộc Nhiêu" });
        jBoxTypeFromStockup.setSelectedItem(null);
        jBoxTypeFromStockup.setFont(fontPlain);
        jBoxTypeFromStockup.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 2;
        gbc.gridy = 0;
        jPanelFilter.add(jBoxTypeFromStockup, gbc);

        jBoxTypeToStockup = new JComboBox<>(new String[] { "Móng Cái", "Ỉ", "Mán", "Sóc", "Cỏ", "Lũng Pù", "Vân Pa",
        "Mường Khương", "Mẹo", "Táp Ná", "Yorkshire", "Landrace", "Pietrain", "Hampshire", "Berkshire",
        "Cornwall", "Ba Xuyên", "Thuộc Nhiêu" });
        jBoxTypeToStockup.setSelectedItem(null);
        jBoxTypeToStockup.setFont(fontPlain);
        jBoxTypeToStockup.setPreferredSize(new Dimension(150, 30));
        gbc.gridy = 1;
        jPanelFilter.add(jBoxTypeToStockup, gbc);

        // ++ add quantity field ++
        jFieldQuantityFromStockup = new JTextField();
        jFieldQuantityFromStockup.setFont(fontPlain);
        jFieldQuantityFromStockup.setPreferredSize(new Dimension(70, 30));
        gbc.gridx = 3;
        gbc.gridy = 0;
        jPanelFilter.add(jFieldQuantityFromStockup, gbc);

        jFieldQuantityToStockup = new JTextField();
        jFieldQuantityToStockup.setFont(fontPlain);
        jFieldQuantityToStockup.setPreferredSize(new Dimension(70, 30));
        gbc.gridy = 1;
        jPanelFilter.add(jFieldQuantityToStockup, gbc);

        // ++ add price field ++
        jFieldPriceFromStockup = new JTextField();
        jFieldPriceFromStockup.setFont(fontPlain);
        jFieldPriceFromStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridx = 4;
        gbc.gridy = 0;
        jPanelFilter.add(jFieldPriceFromStockup, gbc);

        jFieldPriceToStockup = new JTextField();
        jFieldPriceToStockup.setFont(fontPlain);
        jFieldPriceToStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridy = 1;
        jPanelFilter.add(jFieldPriceToStockup, gbc);

        // ++ add age field ++
        jFieldAgeFromStockup = new JTextField();
        jFieldAgeFromStockup.setFont(fontPlain);
        jFieldAgeFromStockup.setPreferredSize(new Dimension(50, 30));
        gbc.gridx = 5;
        gbc.gridy = 0;
        jPanelFilter.add(jFieldAgeFromStockup, gbc);

        jFieldAgeToStockup = new JTextField();
        jFieldAgeToStockup.setFont(fontPlain);
        jFieldAgeToStockup.setPreferredSize(new Dimension(50, 30));
        gbc.gridy = 1;
        jPanelFilter.add(jFieldAgeToStockup, gbc);

        // ++ add date field ++
        jFieldDateFromStockup = new JTextField();
        jFieldDateFromStockup.setFont(fontPlain);
        jFieldDateFromStockup.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 6;
        gbc.gridy = 0;
        jPanelFilter.add(jFieldDateFromStockup, gbc);

        jFieldDateToStockup = new JTextField();
        jFieldDateToStockup.setFont(fontPlain);
        jFieldDateToStockup.setPreferredSize(new Dimension(100, 30));
        gbc.gridy = 1;
        jPanelFilter.add(jFieldDateToStockup, gbc);

        // ++ add vaccine status filed ++
        jBoxVaccineStockup = new JComboBox<>(new String[] {"true", "false"});
        jBoxVaccineStockup.setSelectedItem(null);
        jBoxVaccineStockup.setFont(fontPlain);
        jBoxVaccineStockup.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 7;
        gbc.gridy = 0;
        insets.set(0, 0, 0, 40);
        jPanelFilter.add(jBoxVaccineStockup, gbc);

        // ++ add option field ++
        jBoxOptionStock = new JComboBox<>(new String[] { "AND", "OR" });
        jBoxOptionStock.setSelectedItem(null);
        jBoxOptionStock.setFont(fontBold);
        jBoxOptionStock.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 8;
        gbc.gridy = 0;
        jPanelFilter.add(jBoxOptionStock, gbc);

        // ++ add filter button ++
        JButton jButtonFilter = new JButton("Filter");
        jButtonFilter.setFont(fontBold);
        gbc.gridy = 1;
        jPanelFilter.add(jButtonFilter, gbc);

        // --- add table ---
        jTable = new JTable(sManager.getdModel());

        // --- add to stockup view ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        stockupView.add(jPanelSearch, gbc);

        gbc.gridy = 1;
        stockupView.add(jPanelFilter, gbc);

        gbc.gridy = 2;
        stockupView.add(new JScrollPane(jTable), gbc);

        return stockupView;
    }

    public JPanel customerView() {
        JPanel customerView = new JPanel();

        return customerView;
    }

    public void exit() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "WARNING",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    public void menuStockup() {
        jItemStockup.setEnabled(false);
        jItemCustomer.setEnabled(true);
        System.out.println("stockup");
    }

    public void menuCustomer() {
        jItemCustomer.setEnabled(false);
        jItemStockup.setEnabled(true);
        System.out.println("customer");
    }
}
