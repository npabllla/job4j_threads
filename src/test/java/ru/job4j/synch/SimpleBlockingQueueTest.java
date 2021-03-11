package ru.job4j.synch;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            try {
                queue.offer(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                queue.offer(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread consumer = new Thread(() -> {
            int value = 0;
            try {
                value = queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertThat(value, is(1));
            try {
                value = queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertThat(value, is(2));
        });
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
    }
}