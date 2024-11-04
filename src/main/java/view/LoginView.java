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

    private JTextField jFieldUsernameLogin;
    private JPasswordField jFieldPasswordLogin;
    private JTextField jFieldUsernameSignup;
    private JPasswordField jFieldPasswordSignup;
    private JButton jButtonLogin;
    private JButton jButtonSignup;
    private JButton jButtonCreate;
    private JButton jButtonCancel;

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
        mainView.add(signupView(), "signupview");
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
        Font fontPlain = new Font("Roboto", Font.PLAIN, 15);
        jFieldUsernameLogin = new JTextField();
        jFieldUsernameLogin.setFont(fontPlain);
        Dimension dimension = new Dimension(200, 30);
        jFieldUsernameLogin.setPreferredSize(dimension);
        
        jPanelLogin.add(jFieldUsernameLogin, gbc);

        // set password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 15);
        JLabel jLabelPassword = new JLabel("Password");
        jLabelPassword.setFont(fontBold);

        jPanelLogin.add(jLabelPassword, gbc);

        // set password textfield
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        jFieldPasswordLogin = new JPasswordField();
        jFieldPasswordLogin.setPreferredSize(dimension);

        jPanelLogin.add(jFieldPasswordLogin, gbc);

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

    public JPanel signupView() {
        JPanel jPanelSignup = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- set sign up view ---
        // set username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 15);
        gbc.anchor = GridBagConstraints.EAST;
        JLabel jLabelUsername = new JLabel("Username");
        Font fontBold = new Font("Roboto", Font.BOLD, 15);
        jLabelUsername.setFont(fontBold);

        jPanelSignup.add(jLabelUsername, gbc);

        // set username textfield
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        Font fontPlain = new Font("Roboto", Font.PLAIN, 15);
        jFieldUsernameSignup = new JTextField();
        jFieldUsernameSignup.setFont(fontPlain);
        Dimension dimension = new Dimension(200, 30);
        jFieldUsernameSignup.setPreferredSize(dimension);
        
        jPanelSignup.add(jFieldUsernameSignup, gbc);

        // set password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 15);
        JLabel jLabelPassword = new JLabel("Password");
        jLabelPassword.setFont(fontBold);

        jPanelSignup.add(jLabelPassword, gbc);

        // set password textfield
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        jFieldPasswordSignup = new JPasswordField();
        jFieldPasswordSignup.setPreferredSize(dimension);

        jPanelSignup.add(jFieldPasswordSignup, gbc);

        // set button create
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(30, 50, 0, 0);
        jButtonCreate = new JButton("Create");
        jButtonCreate.setFont(fontBold);
        ActionListener actionListener = new LoginController(this);
        jButtonCreate.addActionListener(actionListener);
        
        jPanelSignup.add(jButtonCreate, gbc);

        // set button cancel
        gbc.gridx = 1;
        gbc.insets = new Insets(30, 40, 0, 0);
        jButtonCancel = new JButton("Cancel");
        jButtonCancel.setFont(fontBold);
        jButtonCancel.addActionListener(actionListener);

        jPanelSignup.add(jButtonCancel, gbc);

        return jPanelSignup;
    }

    public void resetLogin() {
        jFieldUsernameLogin.setText("");
        jFieldPasswordLogin.setText("");
    }

    public void resetSignup() {
        jFieldUsernameSignup.setText("");
        jFieldPasswordSignup.setText("");
    }

    public void signup() {
        cardLayout.last(mainView);
    }

    public void login() {
        String username = jFieldUsernameLogin.getText();
        String password = new String(jFieldPasswordLogin.getPassword());
        try {
            if (cManager.checkLogin(username, password)) {
                JOptionPane.showMessageDialog(null, "Successfully!", "CLM", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                resetLogin();
                JOptionPane.showMessageDialog(null, "Username or password is incorrect!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InputMismatchException ime) {
            JOptionPane.showMessageDialog(null, ime.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public void cancel() {
        resetLogin();
        cardLayout.first(mainView);
    }

    public void create() {
        String username = jFieldUsernameSignup.getText();
        String password = new String(jFieldPasswordSignup.getPassword());
        try {
            if (cManager.checkSignup(username, password)) {
                resetLogin();
                resetSignup();
                cardLayout.first(mainView);
                JOptionPane.showMessageDialog(null, "Account created successfully. Please log in to continue!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                resetSignup();
                resetLogin();
                JOptionPane.showMessageDialog(null, "Username already exits. Try another!", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InputMismatchException ime) {
            JOptionPane.showMessageDialog(null, ime.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
