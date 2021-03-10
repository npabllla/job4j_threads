package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.collection.SimpleArray;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final SimpleArray<T> array = new SimpleArray<>();

    public synchronized void add(T value) {
        array.add(value);
    }

    public synchronized T get(int index) {
        return array.get(index);
    }

    private SimpleArray<T> copy(SimpleArray<T> array) {
        SimpleArray<T> result = new SimpleArray<>();
        for (T value : array) {
            result.add(value);
        }
        return result;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.array).iterator();
    }
}