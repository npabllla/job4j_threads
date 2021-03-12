package ru.job4j.cache;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;

public class CacheTest {

    @Test
    public void whenAddReturnTrue() {
        Cache cache = new Cache();
        Base testBase = new Base(1, 1);
        assertThat(cache.add(testBase), is(true));
    }

    @Test
    public void whenAddReturnFalse() {
        Cache cache = new Cache();
        Base testBase = new Base(1, 1);
        cache.add(testBase);
        assertThat(cache.add(testBase), is(false));
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base testBase = new Base(1, 1);
        cache.delete(testBase);
        assertThat(cache.add(testBase), is(true));
    }

    @Test
    public void whenUpdateReturnTrue() {
        Cache cache = new Cache();
        Base testBase = new Base(1, 1);
        cache.add(testBase);
        testBase.setName("Test");
        assertThat(cache.update(testBase), is(true));
        assertThat("Test", is(cache.get(testBase).getName()));
    }

    @Test
    public void whenUpdateReturnFalse() {
        Cache cache = new Cache();
        Base testBase = new Base(1, 1);
        assertThat(cache.update(testBase), is(false));
    }

    @Test(expected = OptimisticException.class)
    public void whenOptimisticException() {
        Cache cache = new Cache();
        Base testBase = new Base(1, 1);
        assertThat(cache.add(testBase), is(true));
        Base testBaseCopy = new Base(1, 11);
        cache.update(testBaseCopy);
    }
}