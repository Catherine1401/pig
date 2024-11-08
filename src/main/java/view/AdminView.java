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
    private JTable jTableStockup;
    private JTextField jFieldIdStockup;
    private JComboBox<String> jBoxTypeStockup;
    private JTextField jFieldQuantityStockup;
    private JTextField jFieldPriceStockup;
    private JTextField jFieldAgeStockup;
    private JTextField jFieldDateStockup;
    private JComboBox<String> jBoxVaccineInfoStockup;

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
        Insets insets = new Insets(0, 0, 0, 30);
        gbc.insets = insets;
        jPanelSearch.add(jFieldSearchStockup, gbc);

        // ++ add search button ++
        JButton jButtonSearch = new JButton("Search");
        jButtonSearch.setFont(fontBold);
        gbc.gridx = 1;
        jPanelSearch.add(jButtonSearch, gbc);

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
        jTableStockup = new JTable(sManager.getdModel());
        StockupManager.initTable(jTableStockup);
        JPanel jPanelTable = new JPanel();
        jPanelTable.setLayout(null);
        jPanelTable.setPreferredSize(new Dimension(1000, 200));
        JScrollPane jPane = new JScrollPane(jTableStockup);
        jPane.setBounds(89,0,720,200);
        jPanelTable.add(jPane);

        // --- add info ---
        JPanel jPanelInfoStockup = new JPanel(new GridBagLayout());
        JPanel jPanelInfoLeftStockup = new JPanel(new GridBagLayout());
        JPanel jPanelInfoRightStockup = new JPanel(new GridBagLayout());
        // ++ add id ++
        JLabel jLabelIdStockup = new JLabel("ID");
        jLabelIdStockup.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 0;
        insets.set(0, 0, 10, 10);
        jPanelInfoLeftStockup.add(jLabelIdStockup, gbc);

        jFieldIdStockup = new JTextField();
        jFieldIdStockup.setFont(fontPlain);
        jFieldIdStockup.setPreferredSize(new Dimension(120, 30));
        jFieldIdStockup.setEditable(false);
        gbc.gridx = 1;
        jPanelInfoLeftStockup.add(jFieldIdStockup, gbc);

        // ++ add type ++
        JLabel jLabelTypeStockup = new JLabel("Giống Lợn");
        jLabelTypeStockup.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 1;
        jPanelInfoLeftStockup.add(jLabelTypeStockup, gbc);

        jBoxTypeStockup = new JComboBox<>(new String[] { "Móng Cái", "Ỉ", "Mán", "Sóc", "Cỏ", "Lũng Pù", "Vân Pa",
        "Mường Khương", "Mẹo", "Táp Ná", "Yorkshire", "Landrace", "Pietrain", "Hampshire", "Berkshire",
        "Cornwall", "Ba Xuyên", "Thuộc Nhiêu" });
        jBoxTypeStockup.setSelectedItem(null);
        jBoxTypeStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridx = 1;
        jPanelInfoLeftStockup.add(jBoxTypeStockup, gbc);

        // ++ add quantity ++
        JLabel jLabelQuantityStockup = new JLabel("Số Lượng");
        jLabelQuantityStockup.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 2;
        jPanelInfoLeftStockup.add(jLabelQuantityStockup, gbc);

        jFieldQuantityStockup = new JTextField();
        jFieldQuantityStockup.setFont(fontPlain);
        jFieldQuantityStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridx = 1;
        jPanelInfoLeftStockup.add(jFieldQuantityStockup, gbc);

        // ++ add price ++
        JLabel jLabelPriceStockup = new JLabel("Giá");
        jLabelPriceStockup.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 3;
        jPanelInfoLeftStockup.add(jLabelPriceStockup, gbc);

        jFieldPriceStockup = new JTextField();
        jFieldPriceStockup.setFont(fontPlain);
        jFieldPriceStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridx = 1;
        jPanelInfoLeftStockup.add(jFieldPriceStockup, gbc);

        // ++ add age ++
        JLabel jLabelAgeStockup = new JLabel("Ngày Tuổi");
        jLabelAgeStockup.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanelInfoRightStockup.add(jLabelAgeStockup, gbc);

        jFieldAgeStockup = new JTextField();
        jFieldAgeStockup.setFont(fontPlain);
        jFieldAgeStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridx = 1;
        jPanelInfoRightStockup.add(jFieldAgeStockup, gbc);

        // ++ add date ++
        JLabel jLabelDateStockup = new JLabel("Ngày Nhập");
        jLabelDateStockup.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 1;
        jPanelInfoRightStockup.add(jLabelDateStockup, gbc);

        jFieldDateStockup = new JTextField();
        jFieldDateStockup.setFont(fontPlain);
        jFieldDateStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridx = 1;
        jPanelInfoRightStockup.add(jFieldDateStockup, gbc);

        // ++ add vaccine ++
        JLabel jLabelVaccineStockup = new JLabel("Tiêm Chủng");
        jLabelVaccineStockup.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 2;
        jPanelInfoRightStockup.add(jLabelVaccineStockup, gbc);

        jBoxVaccineInfoStockup = new JComboBox<>(new String[] {"true", "false"});
        jBoxVaccineInfoStockup.setSelectedItem(null);
        jBoxVaccineInfoStockup.setFont(fontPlain);
        jBoxVaccineInfoStockup.setPreferredSize(new Dimension(120, 30));
        gbc.gridx = 1;
        jPanelInfoRightStockup.add(jBoxVaccineInfoStockup, gbc);

        
        // ++ add all ++
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 100);
        jPanelInfoStockup.add(jPanelInfoLeftStockup, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        jPanelInfoStockup.add(jPanelInfoRightStockup, gbc);












        
        // --- add to stockup view ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        stockupView.add(jPanelSearch, gbc);

        gbc.gridy = 1;
        stockupView.add(jPanelFilter, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        stockupView.add(jPanelTable, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        stockupView.add(jPanelInfoStockup, gbc);

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
