package ru.job4j.design.lsp.entity;

import java.time.LocalDate;

public class Food {
    private String name;
    private LocalDate createDate;
    private LocalDate expiryDate;
    private double price;
    private double discount = 0.0;

    public Food(String name, LocalDate createDate, LocalDate expiryDate, double price) {
        this.name = name;
        this.createDate = createDate;
        this.expiryDate = expiryDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Food{"
                + "name='"
                + name
                + '\''
                + ", createDate="
                + createDate
                + ", expiryDate="
                + expiryDate
                + ", price="
                + price
                + ", discount="
                + discount
                + '}';
    }
}
