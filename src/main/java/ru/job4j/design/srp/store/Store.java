package ru.job4j.design.srp.store;

import ru.job4j.design.srp.entity.Employee;

import java.util.List;
import java.util.function.Predicate;

public interface Store {
    List<Employee> findBy(Predicate<Employee> filter);
}