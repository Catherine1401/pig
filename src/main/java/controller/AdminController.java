package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AdminView;

public class AdminController implements ActionListener {
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
            case "Search" -> view.search();
            case "Filter" -> view.filter();
        }
    }

}
