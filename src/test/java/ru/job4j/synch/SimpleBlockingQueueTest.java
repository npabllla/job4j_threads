package ru.job4j.synch;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        queue::offer
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(List.of(0, 1, 2, 3, 4)));
    }
}