package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class ParallelSearchIndexTest {

    @Test
    public void whenSearchInLessThan10() {
        Integer[] array = new Integer[]{2,3,5,8};
        Integer element = 5;
        ParallelSearchIndex<Integer> parallelSearchIndex = new ParallelSearchIndex<>(array, element, 0, array.length);
        assertThat(parallelSearchIndex.search(), is(2));
    }

    @Test
    public void whenSearchInMoreThan10() {
        Integer[] array = new Integer[]{2,3,5,8,9,10,11,12,13,14,15};
        Integer element = 5;
        ParallelSearchIndex<Integer> parallelSearchIndex = new ParallelSearchIndex<>(array, element, 0, array.length);
        assertThat(parallelSearchIndex.search(), is(2));
    }

}