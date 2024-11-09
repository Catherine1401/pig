package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.CustomerView;

public class CustomerController implements ActionListener, MouseListener {
    private CustomerView view;

    public CustomerController(CustomerView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String clm = e.getActionCommand();
        switch (clm) {
            case "Exit" -> view.exit();
            case "Purchase" -> view.menuPurchase();
            case "History" -> view.menuPurchase();
            case "BUY" -> view.buy();
            case "CANCEL" -> view.cancel();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        view.selectTable();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
