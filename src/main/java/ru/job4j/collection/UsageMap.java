package ru.job4j.collection;

import java.util.HashMap;

public class UsageMap {
    public static void main(String[] args) {
        HashMap<String, String> users = new HashMap<>();
        users.put("parsentev@yandex.ru", "Petr Arsentev");
        users.put("zv@yandex.ru", "Zolotukhin Vladimir");
        for (String key : users.keySet()) {
            String userInfo = users.get(key);
            System.out.println(userInfo);
        }
    }
}
