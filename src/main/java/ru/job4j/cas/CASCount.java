package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int current = count.get();
        int next = current + 1;
        while (!count.compareAndSet(current, next)) {
            next++;
        }
    }

    public int get() {
        return count.get();
    }
}