package pig;


import javax.swing.UIManager;

import view.LoginView;

public class Pig {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
        new LoginView();
        
    }
}