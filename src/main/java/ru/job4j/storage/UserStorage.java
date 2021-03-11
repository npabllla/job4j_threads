package ru.job4j.storage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        if (users.get(user.getId()) == null) {
            users.put(user.getId(), user);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId()) != null;
    }

    public synchronized void transfer(int fromId, int told, int amount) {
        User userFrom = users.get(fromId);
        User userTo = users.get(told);
        if (userFrom != null && userTo != null && userFrom.getAmount() >= amount) {
            users.put(fromId, new User(userFrom.getId(), userFrom.getAmount() - amount));
            users.put(told, new User(userTo.getId(), userTo.getAmount() + amount));
        }
    }
}
