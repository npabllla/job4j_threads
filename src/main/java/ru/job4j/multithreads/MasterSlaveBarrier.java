package ru.job4j.multithreads;

public class MasterSlaveBarrier {
    private boolean flag = true;

    public synchronized void tryMaster() {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void trySlave() {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void doneMaster() {
        flag = false;
        notifyAll();
    }

    public synchronized void doneSlave() {
        flag = true;
        notifyAll();
    }
}