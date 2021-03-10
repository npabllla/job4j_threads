package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final List<User> users = new ArrayList<>();

    public synchronized boolean add(User user) {
        return users.add(user);
    }

    public synchronized boolean update(User user) {
        if (users.size() > user.getId()) {
            users.set(user.getId(), new User(user.getId(), user.getAmount()));
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(User user) {
        return users.remove(user);
    }

    public synchronized void transfer(int fromId, int told, int amount) {
        if (users.size() < fromId || users.size() < told) {
            throw new IndexOutOfBoundsException();
        } else {
            User userFrom = users.get(fromId);
            User userTo = users.get(told);
            users.set(fromId, new User(userFrom.getId(), userFrom.getAmount() - amount));
            users.set(told, new User(userTo.getId(), userTo.getAmount() + amount));
        }
    }
}
