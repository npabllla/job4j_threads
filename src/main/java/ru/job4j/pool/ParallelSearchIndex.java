package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T extends Comparable<? super T>> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T element;
    private int from;
    private int to;

    public ParallelSearchIndex(T[] array, T element, int from, int to) {
        this.array = array;
        this.element = element;
        this.from = from;
        this.to = to;
    }

    public int search() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndex<>(array, element, from, to));
    }

    private int binarySearch() {
        while (from <= to) {
            int mid = (from + to) / 2;
            T guess = array[mid];
            if (guess == element) {
                return mid;
            } else if (guess.compareTo(element) > 0) {
                to = mid - 1;
            } else {
                from = mid + 1;
            }
        }
        return -1;
    }

    @Override
    protected Integer compute() {
        if ((to - from) <= 10) {
            return binarySearch();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndex<T> leftPart = new ParallelSearchIndex<>(this.array, element, from, mid);
        ParallelSearchIndex<T> rightPart = new ParallelSearchIndex<>(this.array, element, mid + 1, to);
        leftPart.fork();
        rightPart.fork();
        return leftPart.join() + rightPart.join()  + 1;
    }
}
