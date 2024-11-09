package view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.InputMismatchException;

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

import controller.CustomerController;
import model.manager.PurchaseManager;

public class CustomerView extends JFrame {
    private JPanel mainView;
    private CardLayout cardLayout;

    private JMenuItem jItemPurchase;
    private JMenuItem jItemHistory;
    private JTable jTablePurchase;
    private JComboBox<String> jBoxTypePurchase;
    private JTextField jFieldQuantityPurchase;
    private JTextField jFieldPricePurchase;

    private final PurchaseManager pManager = new PurchaseManager();

    public CustomerView() {
        init();
        setVisible(true);
    }

    public void init() {
        setTitle("CUSTOMER");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainView = new JPanel(cardLayout);

        mainView.add(purchaseView(), "purchase");
        mainView.add(historyView(), "history");
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
        ActionListener ac = new CustomerController(this);
        jItemExit.addActionListener(ac);

        jItemPurchase = new JMenuItem("Purchase");
        jItemPurchase.setFont(fontMenu);
        jItemPurchase.setAccelerator(KeyStroke.getKeyStroke("alt S"));
        jItemPurchase.addActionListener(ac);

        jItemHistory = new JMenuItem("History");
        jItemHistory.setFont(fontMenu);
        jItemHistory.setAccelerator(KeyStroke.getKeyStroke("alt C"));
        jItemHistory.setEnabled(false);
        jItemHistory.addActionListener(ac);

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
        jMenuView.add(jItemPurchase);
        jMenuView.add(jItemHistory);
        jMenuHelp.add(jItemDocument);
        jMenuHelp.add(jItemAbout);

        jMenuBar.add(jMenuFile);
        jMenuBar.add(jMenuView);
        jMenuBar.add(jMenuHelp);

        setJMenuBar(jMenuBar);
    }

    public JPanel purchaseView() {
        JPanel purchaseView = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Font fontPlain = new Font("Roboto", Font.PLAIN, 15);
        Font fontBold = new Font("Roboto", Font.BOLD, 15);
        Insets insets = new Insets(0, 0, 0, 0);
        gbc.insets = insets;
        ActionListener aListener = new CustomerController(this);

       
        // --- add table ---
        jTablePurchase = new JTable(pManager.getdModel()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // jTablePurchase.setRowSorter(pManager.gettSorter());
        pManager.initTable(jTablePurchase);
        MouseListener mListener = new CustomerController(this);
        jTablePurchase.addMouseListener(mListener);
        JPanel jPanelTable = new JPanel();
        jPanelTable.setLayout(null);
        jPanelTable.setPreferredSize(new Dimension(1000, 200));
        JScrollPane jPane = new JScrollPane(jTablePurchase);
        jPane.setBounds(89, 0, 720, 200);
        jPanelTable.add(jPane);

        // --- add info ---
        JPanel jPanelInfoPurchase = new JPanel(new GridBagLayout());
        JPanel jPanelInfoLeftPurchase = new JPanel(new GridBagLayout());
        JPanel jPanelInfoRightPurchase = new JPanel(new GridBagLayout());

        // ++ add type ++
        JLabel jLabelTypePurchase = new JLabel("Giống Lợn");
        jLabelTypePurchase.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 0;
        insets.set(0, 0, 10, 20);
        jPanelInfoLeftPurchase.add(jLabelTypePurchase, gbc);

        jBoxTypePurchase = new JComboBox<>(new String[] { "Móng Cái", "Ỉ", "Mán", "Sóc", "Cỏ", "Lũng Pù", "Vân Pa",
                "Mường Khương", "Mẹo", "Táp Ná", "Yorkshire", "Landrace", "Pietrain", "Hampshire", "Berkshire",
                "Cornwall", "Ba Xuyên", "Thuộc Nhiêu" });
        jBoxTypePurchase.setSelectedItem(null);
        jBoxTypePurchase.setEnabled(false);;
        jBoxTypePurchase.setFont(fontPlain);
        jBoxTypePurchase.setPreferredSize(new Dimension(160, 30));
        gbc.gridx = 1;
        jPanelInfoLeftPurchase.add(jBoxTypePurchase, gbc);

        // ++ add quantity ++
        JLabel jLabelQuantityPurchase = new JLabel("Số Lượng");
        jLabelQuantityPurchase.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 1;
        jPanelInfoLeftPurchase.add(jLabelQuantityPurchase, gbc);

        jFieldQuantityPurchase = new JTextField();
        jFieldQuantityPurchase.setFont(fontPlain);
        jFieldQuantityPurchase.setPreferredSize(new Dimension(160, 30));
        gbc.gridx = 1;
        jPanelInfoLeftPurchase.add(jFieldQuantityPurchase, gbc);

        // ++ add price ++
        JLabel jLabelPricePurchase = new JLabel("Giá");
        jLabelPricePurchase.setFont(fontBold);
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanelInfoRightPurchase.add(jLabelPricePurchase, gbc);

        jFieldPricePurchase = new JTextField();
        jFieldPricePurchase.setEditable(false);
        jFieldPricePurchase.setFont(fontPlain);
        jFieldPricePurchase.setPreferredSize(new Dimension(160, 30));
        gbc.gridx = 1;
        jPanelInfoRightPurchase.add(jFieldPricePurchase, gbc);


        // ++ add all ++
        gbc.gridx = 0;
        gbc.gridy = 0;
        insets.set(0, 0, 15, 50);
        jPanelInfoPurchase.add(jPanelInfoLeftPurchase, gbc);
        gbc.gridx = 1;
        insets.set(0, 0, 15, 0);
        jPanelInfoPurchase.add(jPanelInfoRightPurchase, gbc);

        // --- add button ---
        JPanel jPanelButton = new JPanel(new GridBagLayout());

        // ++ add add button ++
        JButton jButtonAddPurchase = new JButton("BUY");
        jButtonAddPurchase.setFont(fontBold);
        jButtonAddPurchase.addActionListener(aListener);
        gbc.gridx = 0;
        gbc.gridy = 0;
        insets.set(0, 0, 0, 20);
        jPanelButton.add(jButtonAddPurchase, gbc);

        // ++ add cancel button ++
        JButton jButtonCancelPurchase = new JButton("CANCEL");
        jButtonCancelPurchase.setFont(fontBold);
        jButtonCancelPurchase.addActionListener(aListener);
        gbc.gridx = 3;
        insets.set(0, 0, 0, 120);
        jPanelButton.add(jButtonCancelPurchase, gbc);

        // --- add to purchase view ---
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        purchaseView.add(jPanelTable, gbc);

        gbc.gridy = 1;
        purchaseView.add(jPanelInfoPurchase, gbc);

        gbc.gridy = 2;
        purchaseView.add(jPanelButton, gbc);

        return purchaseView;
    }

    public JPanel historyView() {
        JPanel customerView = new JPanel();

        return customerView;
    }

    public void exit() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "WARNING",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    public void menuPurchase() {
        jItemPurchase.setEnabled(false);
        jItemHistory.setEnabled(true);
    }

    public void menuHistory() {
        jItemHistory.setEnabled(false);
        jItemPurchase.setEnabled(true);
    }

    public void selectTable() {
        int selectRow = jTablePurchase.getSelectedRow();
        if (selectRow != -1) {
            jBoxTypePurchase.setSelectedItem(jTablePurchase.getValueAt(selectRow, 0));
            jFieldPricePurchase.setText(jTablePurchase.getValueAt(selectRow, 2).toString());
        }
    }

    public void buy() {
        try {
            String type = jBoxTypePurchase.getSelectedItem() == null ? "" : jBoxTypePurchase.getSelectedItem().toString();
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy?", "CONFIRM",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                pManager.buy(type, jFieldQuantityPurchase.getText(), jFieldPricePurchase.getText());
                reset();
                JOptionPane.showMessageDialog(null, "Bought successfully!", "INFORMATION", JOptionPane.OK_OPTION);
            }
        } catch (InputMismatchException ime) {
            JOptionPane.showMessageDialog(null, ime.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cancel() {
        reset();
    }

    public void reset() {
        pManager.input();
        jTablePurchase.setModel(pManager.getdModel());
        pManager.initTable(jTablePurchase);
        jBoxTypePurchase.setSelectedItem(null);
        jFieldQuantityPurchase.setText("");
        jFieldPricePurchase.setText("");

    }
}
