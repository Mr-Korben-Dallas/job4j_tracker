package ru.job4j.design.lsp.store;

import ru.job4j.design.lsp.entity.Food;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

public interface Store {
    List<Food> findBy(Predicate<Food> filter);

    boolean add(Food food);

    default double expirationPercent(LocalDate create, LocalDate expiry) {
        LocalDate currentTime = LocalDate.now();
        long differenceCreateExpiry = create.until(expiry, ChronoUnit.DAYS);
        long differenceCurrentCreate = create.until(currentTime, ChronoUnit.DAYS);
        return (double) differenceCurrentCreate / differenceCreateExpiry * 100;
    }
}
