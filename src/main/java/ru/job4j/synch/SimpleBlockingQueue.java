package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private int amountOfElements = 0;

    public synchronized void offer(T value) throws InterruptedException {
        while (!queue.offer(value) && amountOfElements == queue.size()) {
            this.wait();
        }
        amountOfElements++;
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        this.notifyAll();
        amountOfElements--;
        return queue.poll();
    }
}
