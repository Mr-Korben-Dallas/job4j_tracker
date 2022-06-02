package ru.job4j.design.srp.report;

import ru.job4j.design.srp.entity.Employee;

import java.util.function.Predicate;

public interface Report {
    String generate(Predicate<Employee> filter);
}