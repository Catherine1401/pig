package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginController;
import model.manager.CustomerManager;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class LoginView extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainView;

    private final CustomerManager cManager =  new CustomerManager();

    private JTextField jFieldUsername;
    private JPasswordField jFieldPassword;
    private JButton jButtonLogin;
    private JButton jButtonSignup;

    public LoginView() {
        init();
        setVisible(true);
    }

    public void init() {
        setTitle("LOGIN");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainView = new JPanel(cardLayout);
       
        mainView.add(loginView(), "loginview");
        add(mainView);

        cardLayout.first(mainView);
    }

    public JPanel loginView() {
        JPanel jPanelLogin = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

         // --- set login view ---
        // set username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 15);
        gbc.anchor = GridBagConstraints.EAST;
        JLabel jLabelUsername = new JLabel("Username");
        Font fontBold = new Font("Roboto", Font.BOLD, 15);
        jLabelUsername.setFont(fontBold);

        jPanelLogin.add(jLabelUsername, gbc);

        // set username textfield
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        jFieldUsername = new JTextField();
        Font fontPlain = new Font("Roboto", Font.PLAIN, 15);
        jFieldUsername.setFont(fontPlain);
        Dimension dimension = new Dimension(200, 30);
        jFieldUsername.setPreferredSize(dimension);
        
        jPanelLogin.add(jFieldUsername, gbc);

        // set password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 15);
        JLabel jLabelPassword = new JLabel("Password");
        jLabelPassword.setFont(fontBold);

        jPanelLogin.add(jLabelPassword, gbc);

        // set passroed textfield
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        jFieldPassword = new JPasswordField();
        jFieldPassword.setPreferredSize(dimension);

        jPanelLogin.add(jFieldPassword, gbc);

        // set button login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 50, 0, 0);
        jButtonLogin = new JButton("Login");
        jButtonLogin.setFont(fontBold);
        ActionListener actionListener = new LoginController(this);
        jButtonLogin.addActionListener(actionListener);
        
        jPanelLogin.add(jButtonLogin, gbc);

        // set button signup
        gbc.gridx = 1;
        gbc.insets = new Insets(30, 40, 0, 0);
        jButtonSignup = new JButton("Sign up");
        jButtonSignup.setFont(fontBold);
        jButtonSignup.addActionListener(actionListener);

        jPanelLogin.add(jButtonSignup, gbc);

        return jPanelLogin;
    }

    public void login() {
        String username = jFieldUsername.getText();
        String password = new String(jFieldPassword.getPassword());
        try {
            cManager.checkInfo(username, password);
            JOptionPane.showMessageDialog(null, "Successfully!", "successfully", JOptionPane.CLOSED_OPTION);
        } catch (InputMismatchException ime) {
            JOptionPane.showMessageDialog(null, ime.getMessage(), "ERORR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
