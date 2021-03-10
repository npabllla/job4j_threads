package ru.job4j.synch;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
        });
        Thread consumer = new Thread(() -> {
            int value = queue.poll();
            assertThat(value, is(1));
            value = queue.poll();
            assertThat(value, is(2));
        });
        consumer.start();
        producer.start();
        producer.join();
        consumer.join();
    }
}