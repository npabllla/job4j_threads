package ru.job4j.multithreads;

public class Switcher {
    public static void main(String[] args) throws InterruptedException {
        MasterSlaveBarrier masterSlaveBarrier = new MasterSlaveBarrier();
        Thread first = new Thread(
                () -> {
                    while (true) {
                        masterSlaveBarrier.tryMaster();
                        System.out.println("Thread A");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        masterSlaveBarrier.doneMaster();
                    }
                }
        );
        Thread second = new Thread(
                () -> {
                    while (true) {
                        masterSlaveBarrier.trySlave();
                        System.out.println("Thread B");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        masterSlaveBarrier.doneSlave();
                    }
                }
        );
        first.start();
        second.start();
        first.join();
        second.join();
    }
}