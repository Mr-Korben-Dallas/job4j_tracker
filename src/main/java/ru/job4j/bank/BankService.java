package ru.job4j.bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для добавления клиентов в базу,
 * созданию новых счетов, переводов денег между счетами.
 * @author job4j, Zolotukhin Vladimir
 */
public class BankService {
    /**
     * Карта хранящая Пользователя и список его Счетов
     */
    private final Map<User, List<Account>> users = new HashMap<>();

    /**
     * Метод принимает на вход объект класса User и добавляет его в HashMap users.
     * @param user
     */
    public void addUser(User user) {
        users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Метод принимает два параметра - паспорт и объект класса Account.
     * Находим клиента по номеру паспорта и получаем все его существующе аккаунты в список.
     * Сравниваем принятый объект с существующими аккаунтами,
     * если такого нет то добавляем его в список.
     *
     * @param passport
     * @param account
     */
    public void addAccount(String passport, Account account) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> accountList = users.get(user);
            if (!accountList.contains(account)) {
                accountList.add(account);
            }
        }
    }

    /**
     * Метод принимает на вход номер паспорта клиента.
     * Возвращает клиента по паспорту если такой имеется в базе.
     * @param passport
     * @return Возвращает клиента по паспорту если такой имеется в базе.
     */
    public User findByPassport(String passport) {
        for (User user : users.keySet()) {
            if (user.getPassport().equals(passport)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Метод принимает на вход паспорт и реквизиты.
     * По паспорту находим клиента.
     * Если такой имеется, то ищем есть ли такой аккаунт с определенными реквизитами.
     * @param passport
     * @param requisite
     * @return Возвращает аккаунт клиента.
     */
    public Account findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> accounts = users.get(user);
            for (Account account : accounts) {
                if (account.getRequisite().equals(requisite)) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Метод принимает на вход паспорта и реквизиты двух аккаунтов
     * и количество денег для перевода.
     * Осуществляет перевод с первого счета на второй
     * если на первом хватает средств для перевода
     * @return Возвращает результат операции в виде true или false.
     * @param srcPassport
     * @param srcRequisite
     * @param destPassport
     * @param destRequisite
     * @param amount
     *
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite, double amount) {
        boolean rsl = false;
        Account firstAccount = findByRequisite(srcPassport, srcRequisite);
        Account secondAccount = findByRequisite(destPassport, destRequisite);
        if (firstAccount != null && secondAccount != null && firstAccount.getBalance() >= amount) {
            firstAccount.setBalance(firstAccount.getBalance() - amount);
            secondAccount.setBalance(secondAccount.getBalance() + amount);
            rsl = true;
        }

        return rsl;
    }
}