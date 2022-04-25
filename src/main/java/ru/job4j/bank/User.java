package ru.job4j.bank;

import java.util.Objects;

/**
 * Модель данных клиент банка
 * @author job4j
 */
public class User {
    /**
     * Переменная passport хранит данные паспорта клиента.
     * Переменная username хранит ФИО клиента.
     */
    private String passport;
    private String username;

    /**
     * Создаем конструктор для задание полей клиету.
     * @param passport
     * @param username
     */
    public User(String passport, String username) {
        this.passport = passport;
        this.username = username;
    }

    /**
     * Метод возвращает номер паспорта клиента.
     * @return
     */
    public String getPassport() {
        return passport;
    }

    /**
     * Метод позволяет изменить паспорт клиента.
     * @param passport
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * Метод возвращает ФИО клиента.
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Метод позволяет изменить ФИО клиента.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Переопределяем метод equals.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(passport, user.passport);
    }

    /**
     * Переопределям hashCode.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(passport);
    }
}