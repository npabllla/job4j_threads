package ru.job4j.storage;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class User {
    private final int id;
    private final int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return this.id;
    }

    public int getAmount() {
        return this.amount;
    }
}
