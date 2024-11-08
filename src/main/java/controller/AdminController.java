package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.AdminView;

public class AdminController implements ActionListener, MouseListener {
    private AdminView view;

    public AdminController(AdminView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String clm = e.getActionCommand();
        switch (clm) {
            case "Exit" -> view.exit();
            case "Stock Up" -> view.menuStockup();
            case "Customer" -> view.menuCustomer();
            case "Sort" -> view.sort();
            case "Search" -> view.search();
            case "Filter" -> view.filter();
            case "ADD" -> view.add();
            case "DELETE" -> view.delete();
            case "UPDATE" -> view.update();
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
