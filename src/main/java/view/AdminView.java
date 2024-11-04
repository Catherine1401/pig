package view;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import controller.AdminController;

public class AdminView extends JFrame {
    private JPanel mainView;
    private CardLayout cardLayout;

    JMenuItem jItemStockup;
    JMenuItem jItemCustomer;

    public AdminView() {
        init();
        setVisible(true);
    }

    public void init() {
        setTitle("ADMIN");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainView = new JPanel(cardLayout);

        mainView.add(stockupView(), "stockup");
        mainView.add(customerView(), "customer");

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
        jItemClose.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));

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
        JPanel stockupView = new JPanel();

        return stockupView;
    }

    public JPanel customerView() {
        JPanel customerView = new JPanel();

        return customerView;
    }

    public void exit() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "WARNING", JOptionPane.YES_NO_OPTION);
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
