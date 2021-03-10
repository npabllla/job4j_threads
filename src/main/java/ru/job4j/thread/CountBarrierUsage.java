package ru.job4j.thread;

import ru.job4j.CountBarrier;

public class CountBarrierUsage {
    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(15000000);
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 0; i < 15000000; i++) {
                        barrier.count();
                    }
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
