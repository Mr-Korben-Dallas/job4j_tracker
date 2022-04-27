package ru.job4j.search;

import java.util.ArrayList;
import java.util.function.Predicate;

public class PhoneDictionary {
    private ArrayList<Person> persons = new ArrayList<>();

    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, который содержат key в любых полях.
     * @param key Ключ поиска.
     * @return Список подошедщих пользователей.
     */
    public ArrayList<Person> find(String key) {
        Predicate<Person> predicateName = el -> el.getName().contains(key);
        Predicate<Person> predicatePhone = el -> el.getPhone().contains(key);
        Predicate<Person> predicateSurname = el -> el.getSurname().contains(key);
        Predicate<Person> predicateAdress = el -> el.getAddress().contains(key);
        Predicate<Person> combine = predicateName.or(predicatePhone).or(predicateAdress).or(predicateSurname);
        ArrayList<Person> result = new ArrayList<>();
        for (Person person : persons) {
            if (combine.test(person)) {
                result.add(person);
            }
        }
        return result;
    }
}