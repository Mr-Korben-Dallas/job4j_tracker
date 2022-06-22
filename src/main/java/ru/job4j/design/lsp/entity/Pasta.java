package ru.job4j.design.lsp.entity;

import java.time.LocalDate;

public class Pasta extends Food {
    public Pasta(String name, LocalDate createDate, LocalDate expiryDate, double price) {
        super(name, createDate, expiryDate, price);
    }
}
