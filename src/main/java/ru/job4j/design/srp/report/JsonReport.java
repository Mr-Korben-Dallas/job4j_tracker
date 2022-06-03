package ru.job4j.design.srp.report;

import com.google.gson.GsonBuilder;
import ru.job4j.design.srp.entity.Employee;
import ru.job4j.design.srp.store.Store;

import java.util.function.Predicate;

public class JsonReport implements Report {
    private final Store store;

    public JsonReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        return new GsonBuilder().create().toJson(store.findBy(filter));
    }
}
