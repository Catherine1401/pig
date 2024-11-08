package model.order;

import java.time.LocalDate;

public class StockupOrder {
    private int id, quantity, age;
    private String type;
    private long price;
    private boolean vaccineStatus;
    private LocalDate date;

    public StockupOrder() {
    }

    public StockupOrder(int id, String type, int quantity, long price, int age, LocalDate date, boolean vaccineStatus) {
        this.id = id;
        this.quantity = quantity;
        this.age = age;
        this.type = type;
        this.price = price;
        this.vaccineStatus = vaccineStatus;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public boolean isVaccineStatus() {
        return vaccineStatus;
    }

    public void setVaccineStatus(boolean vaccineStatus) {
        this.vaccineStatus = vaccineStatus;
    }
}
