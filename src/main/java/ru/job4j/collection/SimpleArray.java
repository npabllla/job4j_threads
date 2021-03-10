package ru.job4j.collection;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {
    private int length = 10;
    private int size = 0;
    private int modCount = 0;
    private Object[] container =  new Object[length];

    public T get(int index) {
        return (T) container[Objects.checkIndex(index, size)];
    }

    public void add(T model) {
        if (size == length) {
            length *= 2;
            container = Arrays.copyOf(container, length);
        }
        container[size++] = model;
        modCount++;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int point = 0;
            final int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                return point < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return (T) container[point++];
            }
        };
    }
}